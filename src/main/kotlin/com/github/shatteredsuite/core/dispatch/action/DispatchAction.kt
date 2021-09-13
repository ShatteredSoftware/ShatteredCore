package com.github.shatteredsuite.core.dispatch.action

import com.github.shatteredsuite.core.attribute.Identified

interface DispatchAction<in StateType> : Identified {
    fun run(state: StateType)
}