package com.github.shatteredsuite.core.dispatch.argument

import com.github.shatteredsuite.core.dispatch.context.CommandContext

interface DispatchOptionalArgument<StateType : CommandContext, T> : DispatchArgument<StateType, T> {
    fun default(state: StateType): T?
}