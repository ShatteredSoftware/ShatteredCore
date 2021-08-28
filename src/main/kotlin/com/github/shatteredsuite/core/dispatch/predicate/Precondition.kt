package com.github.shatteredsuite.core.dispatch.predicate

interface Precondition<StateType : Any> {
    val failureMessageId: String

    fun check(state: StateType): PredicateResult
}