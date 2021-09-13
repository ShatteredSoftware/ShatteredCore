package com.github.shatteredsuite.core.feature

import org.bukkit.entity.Player

class CoreFeatureManager {
    private val map = mutableMapOf<String, CoreFeature>()

    fun get(id: String): CoreFeature? {
        return map[id]
    }

    fun add(feature: CoreFeature) {
        map[feature.id] = feature
    }

    fun canUse(player: Player, id: String): Boolean {
        val feature = get(id) ?: return false
        return feature.enabled && (feature.defaultEnabled || player.hasPermission(feature.permission))
    }

    fun canUse(player: Player, feature: CoreFeature): Boolean {
        return feature.enabled && (feature.defaultEnabled || player.hasPermission(feature.permission))
    }
}