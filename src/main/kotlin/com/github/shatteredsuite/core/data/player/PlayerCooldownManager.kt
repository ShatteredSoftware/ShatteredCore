package com.github.shatteredsuite.core.data.player

import com.github.shatteredsuite.core.feature.CoreFeature
import com.github.shatteredsuite.core.feature.CoreFeatureManager

class PlayerCooldownManager(val featureManager: CoreFeatureManager) {
    val map: Map<String, Map<CoreFeature, Long>> = mutableMapOf()

    fun canUse(player: CorePlayer, feature: CoreFeature) {
    }

    fun use(player: CorePlayer, feature: CoreFeature) {

    }

    fun reset(player: CorePlayer, feature: CoreFeature) {

    }
}