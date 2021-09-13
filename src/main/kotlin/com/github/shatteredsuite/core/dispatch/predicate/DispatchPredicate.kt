package com.github.shatteredsuite.core.dispatch.predicate

interface DispatchPredicate<in StateType> {
    val failureMessageId: String

    fun check(state: StateType): PredicateResult
}