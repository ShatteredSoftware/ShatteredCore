package com.github.shatteredsuite.core.dispatch.predicate.impl

import com.github.shatteredsuite.core.attribute.CommandSenderHolder
import com.github.shatteredsuite.core.dispatch.predicate.DispatchPredicate
import com.github.shatteredsuite.core.dispatch.predicate.PredicateResult
import org.bukkit.entity.Player

object PlayerPredicate : DispatchPredicate<CommandSenderHolder> {
    override fun check(state: CommandSenderHolder): PredicateResult {
        if(state.sender is Player) {
            return PredicateResult(passed = true)
        }
        return PredicateResult(passed = false)
    }

    override val failureMessageId = "no-console"
}