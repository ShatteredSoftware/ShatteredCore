package com.github.shatteredsuite.core.dispatch.command

import com.github.shatteredsuite.core.attribute.Identified
import com.github.shatteredsuite.core.data.generic.GenericDataStore
import com.github.shatteredsuite.core.data.generic.MutableDataStore
import com.github.shatteredsuite.core.dispatch.action.DispatchAction
import com.github.shatteredsuite.core.dispatch.argument.ArgumentValidationResult
import com.github.shatteredsuite.core.dispatch.argument.DispatchArgument
import com.github.shatteredsuite.core.dispatch.argument.DispatchOptionalArgument
import com.github.shatteredsuite.core.dispatch.context.CommandContext
import com.github.shatteredsuite.core.dispatch.predicate.DispatchPredicate

/**
 * Order of operations:
 * * Validate predicates
 * * Validate arguments
 * * Look for children
 *   * If a child matches, pass off handling to that child
 * * Look for optional arguments
 * * Run the given action, passing in data from parsed arguments
 */
class DispatchCommand<StateType : CommandContext>(
    override val id: String,
    private val action: DispatchAction<StateType>,
    private val predicates: List<DispatchPredicate<StateType>> = emptyList(),
    private val arguments: List<DispatchArgument<StateType, *>> = emptyList(),
    private val optionalArguments: List<DispatchOptionalArgument<StateType, *>> = emptyList(),
    private val children: Map<String, DispatchCommand<StateType>> = emptyMap(),
) : Identified {
    companion object {
        fun <T : CommandContext> build(key: String, fn: DispatchCommandBuilder<T>.() -> Unit): DispatchCommand<T> {
            val builder = DispatchCommandBuilder<T>(key)
            fn(builder)
            return builder.build()
        }
    }

    private val argsByPosition: List<Pair<DispatchArgument<StateType, *>, Int>>
    private val requiredArgumentLength = arguments.fold(0) { sum, it -> sum + it.expectedArgs }

    init {
        val argsByPosition = mutableListOf<Pair<DispatchArgument<StateType, *>, Int>>()
        var counter = 0
        for (arg in arguments) {
            for (position in 0..arg.expectedArgs) {
                argsByPosition.add(arg to counter)
            }
            counter += arg.expectedArgs
        }
        this.argsByPosition = argsByPosition
    }

    fun run(
        state: StateType,
        args: List<String>,
        currentState: GenericDataStore = GenericDataStore(),
        debug: Boolean = false
    ) {
        if (failedPredicates(state, debug)) {
            return
        }

        state.debugLog(
            "dispatch.debug.length",
            { GenericDataStore.of("size" to args.size, "required" to requiredArgumentLength) }, state.getLocale()
        )
        if (isInvalidArgLength(args)) {
            logArgLengthFailure(state, currentState)
            return
        }

        val (success, lastIndex) = checkArgs(state, args, currentState, debug)
        if (!success) {
            return
        }

        val child = getChildToRun(state, args, currentState, lastIndex, debug)
        if (child != null) {
            // Pass off handling to children
            child.run(state, args, currentState)
            return
        }

        if (lastIndex < args.size) {
            checkOptionalArgs(state, args, currentState, lastIndex, debug)
        }

        action.run(state)
    }

    fun complete(
        state: StateType,
        args: List<String>,
        currentState: GenericDataStore = GenericDataStore(),
        debug: Boolean = false
    ): List<String> {
        if (failedPredicates(state, debug)) {
            return emptyList()
        }

        if (args.size >= requiredArgumentLength) {
            val child = children[args[requiredArgumentLength]]
            if (child != null) {
                return child.complete(state, args.subList(requiredArgumentLength, args.size), currentState, debug)
            }
        }

        val (currentArg, startingIndex) = argsByPosition[args.size]
        return currentArg.complete(args, startingIndex, state)
    }

    private fun failedPredicates(state: StateType, debug: Boolean): Boolean {
        val results = predicates.map { it.check(state, debug) }
        val predicateResults = results.zip(predicates)
        val failures = predicateResults.filter { (result, _) -> !result.passed }
        if (failures.isNotEmpty()) {
            failures.forEach { (result, predicate) ->
                state.log(predicate.failureMessageId, result.data, state.getLocale())
            }
            return false
        }
        return true
    }

    private fun isInvalidArgLength(args: List<String>): Boolean {
        if (args.size < requiredArgumentLength) {
            return true
        }
        return false
    }

    private fun logArgLengthFailure(state: StateType, data: MutableDataStore) {
        val usage = arguments.fold(StringBuilder()) { builder, arg ->
            builder.append(state.messageProcessorStore.process(arg.usageId, null, state.getLocale()))
        }
        data["usage"] = usage
        state.log("command.usage", data, state.getLocale())
    }

    private fun checkArgs(
        state: StateType,
        args: List<String>,
        data: MutableDataStore,
        debug: Boolean
    ): Pair<Boolean, Int> {
        var currendIndex = 0
        val failures = mutableListOf<ArgumentValidationResult<*>>()
        for (arg in arguments) {
            state.debugLog(
                "dispatch.debug.argument.start",
                { GenericDataStore.of("arg" to arg.name) },
                state.getLocale()
            )

            val result = arg.validate(args, currendIndex, state)

            if (result.success) {
                state.debugLog(
                    "dispatch.debug.argument.pass",
                    { GenericDataStore.of("arg" to arg.name, "value" to (result.result?.toString() ?: "null")) },
                    state.getLocale()
                )
                data[arg.name] =
                    result.result ?: throw IllegalStateException("${arg.name} came back as null when successful")
            } else {
                state.debugLog(
                    "dispatch.debug.optional.fail",
                    { GenericDataStore.of("arg" to arg.name) },
                    state.getLocale()
                )
                failures.add(result)
            }

            currendIndex += arg.expectedArgs
        }

        if (failures.isNotEmpty()) {
            failures.forEach {
                state.log(it.faliureMessageId, it.data, state.getLocale())
            }
            return true to 0
        }
        return false to currendIndex
    }

    private fun getChildToRun(
        state: StateType,
        args: List<String>,
        currentState: GenericDataStore,
        lastIndex: Int,
        debug: Boolean
    ): DispatchCommand<StateType>? {
        if (children.isNotEmpty()) {
            if (lastIndex >= args.size) {
                return null
            }
            val child = children[args[lastIndex]] ?: return null
            child.run(state, args.slice(lastIndex..args.size), currentState)
            return child
        }
        return null
    }

    private fun checkOptionalArgs(
        state: StateType,
        args: List<String>,
        data: MutableDataStore,
        start: Int,
        debug: Boolean
    ) {
        var current = start
        optionalArguments.forEach { arg ->
            state.debugLog(
                "dispatch.debug.optional.start",
                { GenericDataStore.of("arg" to arg.name) },
                state.getLocale()
            )
            val result = arg.validate(args, current, state)
            if (result.success) {
                state.debugLog(
                    "dispatch.debug.optional.pass",
                    { GenericDataStore.of("arg" to arg.name, "value" to (result.result?.toString() ?: "null")) },
                    state.getLocale()
                )
                data[arg.name] =
                    result.result ?: throw IllegalStateException("${arg.name} came back as null when successful")
                current += arg.expectedArgs
            }
            state.debugLog(
                "dispatch.debug.optional.fail",
                { GenericDataStore.of("arg" to arg.name) },
                state.getLocale()
            )
        }
    }
}
