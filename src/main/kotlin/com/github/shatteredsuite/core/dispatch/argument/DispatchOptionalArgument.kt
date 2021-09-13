package com.github.shatteredsuite.core.dispatch.argument

interface DispatchOptionalArgument<StateType, T> : DispatchArgument<StateType, T> {
    fun default(state: StateType): T?
}