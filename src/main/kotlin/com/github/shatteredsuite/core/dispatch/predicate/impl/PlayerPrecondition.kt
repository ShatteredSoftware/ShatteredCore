package com.github.shatteredsuite.core.dispatch.predicate.impl

import com.github.shatteredsuite.core.attribute.CommandSenderHolder
import com.github.shatteredsuite.core.dispatch.predicate.Precondition
import com.github.shatteredsuite.core.dispatch.predicate.PredicateResult
import org.bukkit.entity.Player

class PlayerPrecondition<T : CommandSenderHolder> : Precondition<T> {
    override fun check(state: T): PredicateResult {
        if(state.getSender() is Player) {
            return PredicateResult(passed = true)
        }
        return PredicateResult(passed = false)
    }

    override val failureMessageId = "no-console"
}