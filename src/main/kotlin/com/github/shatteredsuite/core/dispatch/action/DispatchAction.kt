package com.github.shatteredsuite.core.dispatch.action

import com.github.shatteredsuite.core.attribute.Identified

interface DispatchAction : Identified {
    fun run()
}