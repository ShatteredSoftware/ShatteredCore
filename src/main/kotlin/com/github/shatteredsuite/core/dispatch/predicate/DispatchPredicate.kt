package com.github.shatteredsuite.core.dispatch.predicate

import com.github.shatteredsuite.core.dispatch.context.CommandContext

interface DispatchPredicate<in StateType : CommandContext> {
    val failureMessageId: String

    fun check(state: StateType, debug: Boolean = false): PredicateResult
}