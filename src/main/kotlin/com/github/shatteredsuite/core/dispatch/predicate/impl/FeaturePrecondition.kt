package com.github.shatteredsuite.core.dispatch.predicate.impl

import com.github.shatteredsuite.core.attribute.FeatureUser
import com.github.shatteredsuite.core.data.generic.GenericDataStore
import com.github.shatteredsuite.core.dispatch.predicate.Precondition
import com.github.shatteredsuite.core.dispatch.predicate.PredicateResult
import com.github.shatteredsuite.core.feature.CoreFeature

class FeaturePrecondition<T : FeatureUser>(val feature: CoreFeature) : Precondition<T> {
    override fun check(state: T): PredicateResult {
        if(state.canUse(feature)) {
            return PredicateResult(passed = true)
        }
        return PredicateResult(passed = false, GenericDataStore.of("name" to feature.name, "key" to feature.key, "description" to feature.description, "permission" to feature.permission))
    }

    override val failureMessageId = "feature-no-permission"
}