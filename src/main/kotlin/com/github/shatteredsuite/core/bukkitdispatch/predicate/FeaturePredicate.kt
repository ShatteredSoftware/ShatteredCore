package com.github.shatteredsuite.core.bukkitdispatch.predicate

import com.github.shatteredsuite.core.data.generic.GenericDataStore
import com.github.shatteredsuite.core.bukkitdispatch.context.BukkitCommandContext
import com.github.shatteredsuite.core.dispatch.predicate.DispatchPredicate
import com.github.shatteredsuite.core.dispatch.predicate.PredicateResult
import com.github.shatteredsuite.core.feature.CoreFeature

class FeaturePredicate(val feature: CoreFeature) : DispatchPredicate<BukkitCommandContext> {
    override fun check(state: BukkitCommandContext, debug: Boolean): PredicateResult {
        state.debugLog("predicate.feature.start", { unwrap(feature) }, state.getLocale())
        if (state.canUse(feature)) {
            state.debugLog("predicate.feature.pass", { unwrap(feature) }, state.getLocale())
            return PredicateResult(passed = true)
        }
        state.debugLog("predicate.feature.fail", { unwrap(feature) }, state.getLocale())
        return PredicateResult(passed = false, unwrap(feature))
    }

    override val failureMessageId = "feature-no-permission"

    private fun unwrap(feature: CoreFeature) = GenericDataStore.of(
        "name" to feature.name,
        "key" to feature.key,
        "description" to feature.description,
        "permission" to feature.permission
    )
}