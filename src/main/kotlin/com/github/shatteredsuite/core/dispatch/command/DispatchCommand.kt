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

    private val argsByPosition: List<DispatchArgument<StateType, *>>
    private val requiredArgumentLength = arguments.fold(0) { sum, it -> sum + it.expectedArgs }

    init {
        val argsByPosition = mutableListOf<DispatchArgument<StateType, *>>()
        for (arg in arguments) {
            for (position in 0..arg.expectedArgs) {
                argsByPosition.add(arg)
            }
        }
        this.argsByPosition = argsByPosition
    }

    fun run(state: StateType, args: List<String>, currentState: GenericDataStore = GenericDataStore()) {
        if (!passedPredicatesOrLogFailures(state)) {
            return
        }

        if (!validArgLength(state, args, currentState)) {
            return
        }

        val (success, lastIndex) = checkArgs(state, args, currentState)
        if (!success) {
            return
        }

        if (children.isNotEmpty()) {
            if (lastIndex >= args.size) {
                return
            }
            val child = children[args[lastIndex]] ?: return
            child.run(state, args.slice(lastIndex..args.size), currentState)
            return
        }

        if (lastIndex < args.size) {
            checkOptionalArgs(state, args, currentState, lastIndex)
        }

        action.run(state)
    }

    private fun checkOptionalArgs(state: StateType, args: List<String>, data: MutableDataStore, start: Int) {
        var current = start
        optionalArguments.forEach { arg ->
            val result = arg.validate(args, current, state)
            if (result.success) {
                data[arg.name] =
                    result.result ?: throw IllegalStateException("${arg.name} came back as null when successful")
                current += arg.expectedArgs
            }
        }
    }

    private fun checkArgs(state: StateType, args: List<String>, data: MutableDataStore): Pair<Boolean, Int> {
        var currendIndex = 0
        val failures = mutableListOf<ArgumentValidationResult<*>>()
        for (arg in arguments) {
            val result = arg.validate(args, currendIndex, state)
            if (result.success) {
                data[arg.name] =
                    result.result ?: throw IllegalStateException("${arg.name} came back as null when successful")
            } else {
                failures.add(result)
            }
            currendIndex += arg.expectedArgs
        }
        if (failures.isNotEmpty()) {
            failures.forEach {
                state.logFailure(it.faliureMessageId, it.data, state.getLocale())
            }
            return false to 0
        }
        return true to currendIndex

    }

    private fun validArgLength(state: StateType, args: List<String>, data: MutableDataStore): Boolean {
        if (args.size < requiredArgumentLength) {
            val usage = arguments.fold(StringBuilder()) { builder, arg ->
                builder.append(state.messageProcessorStore.process(arg.usageId, null, state.getLocale()))
            }
            data["usage"] = usage
            state.logFailure("command.usage", data, state.getLocale())
            return false
        }
        return true
    }

    private fun passedPredicatesOrLogFailures(state: StateType): Boolean {
        val results = predicates.map { it.check(state) }
        val predicateResults = results.zip(predicates)
        val failures = predicateResults.filter { (result, _) -> !result.passed }
        if (failures.isNotEmpty()) {
            failures.forEach { (result, predicate) ->
                state.logFailure(predicate.failureMessageId, result.data, state.getLocale())
            }
            return false
        }
        return true
    }
}