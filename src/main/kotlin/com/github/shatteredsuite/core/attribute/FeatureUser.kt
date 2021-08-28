package com.github.shatteredsuite.core.attribute

import com.github.shatteredsuite.core.feature.CoreFeature

interface FeatureUser {
    fun canUse(feature: CoreFeature): Boolean
}