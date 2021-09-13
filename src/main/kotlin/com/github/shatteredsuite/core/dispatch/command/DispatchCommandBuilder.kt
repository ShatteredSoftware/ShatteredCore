package com.github.shatteredsuite.core.dispatch.command

import com.github.shatteredsuite.core.dispatch.action.DispatchAction
import com.github.shatteredsuite.core.dispatch.argument.DispatchArgument
import com.github.shatteredsuite.core.dispatch.argument.DispatchOptionalArgument
import com.github.shatteredsuite.core.dispatch.context.CommandContext
import com.github.shatteredsuite.core.dispatch.predicate.DispatchPredicate
import com.github.shatteredsuite.core.dispatch.predicate.PredicateResult

class DispatchCommandBuilder<StateType : CommandContext>(val key: String) {
    var action: DispatchAction<StateType> = object : DispatchAction<StateType> {
        override fun run(state: StateType) {
            return
        }

        override val id: String = "core:nothing"
    }

    private val requiredArguments: MutableList<DispatchArgument<StateType, *>> = mutableListOf()
    private val optionalArguments: MutableList<Pair<DispatchOptionalArgument<StateType, *>, Int>> = mutableListOf()
    private val predicates: MutableList<DispatchPredicate<StateType>> = mutableListOf()
    private val children: MutableMap<String, DispatchCommand<StateType>> = mutableMapOf()
    private var nextArg = 0

    fun withArg(param: DispatchArgument<StateType, *>, order: Int = nextArg++) {
        requiredArguments.add(order, param)
    }

    fun withArgs(vararg params: Pair<DispatchArgument<StateType, *>, Int>) {
        params.forEach { (param, order) -> withArg(param, order) }
    }

    fun withOptionalArg(arg: DispatchOptionalArgument<StateType, *>, priority: Int = 1) {
        optionalArguments.add(arg to priority)
    }

    fun check(predicate: DispatchPredicate<StateType>) {
        predicates.add(predicate)
    }

    fun check(fn: (state: StateType) -> PredicateResult, failMessage: String) {
        predicates.add(object : DispatchPredicate<StateType> {
            override val failureMessageId: String = failMessage
            override fun check(state: StateType): PredicateResult = fn(state)
        })
    }

    fun withChild(name: String, fn: DispatchCommandBuilder<StateType>.() -> Unit) {
        val builder = DispatchCommandBuilder<StateType>("$key-$name")
        fn(builder)
        children[name] = builder.build()
    }

    fun build() : DispatchCommand<StateType> {
        optionalArguments.sortBy { (_, priority) -> priority }
        return DispatchCommand(key, action, predicates, requiredArguments, optionalArguments.map { (arg, _) -> arg }, children)
    }

    fun run(action: DispatchAction<StateType>) {
        this.action = action
    }

    fun run(fn: (state: StateType) -> Unit) {
        action = object : DispatchAction<StateType> {
            override val id: String = "$key:action"
            override fun run(state: StateType) = fn(state)
        }
    }
}