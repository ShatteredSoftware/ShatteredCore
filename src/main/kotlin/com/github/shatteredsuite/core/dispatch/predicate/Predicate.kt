package com.github.shatteredsuite.core.dispatch.predicate

interface Predicate<TargetType : Any> {
    val failureMessageId: String

    fun check(sender: TargetType): PredicateResult
}