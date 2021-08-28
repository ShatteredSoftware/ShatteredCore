package com.github.shatteredsuite.core.plugin.feature

import com.github.shatteredsuite.core.attribute.Identified

class ShatteredModule(override val id: String, features: Iterable<CoreServerFeature>? = null) : Identified {
    private val features: MutableSet<CoreServerFeature> = features?.toMutableSet() ?: mutableSetOf()

    fun addFeature(feature: CoreServerFeature) {
        this.features.add(feature)
    }

    fun getFeatures(): Set<CoreServerFeature> {
        return features.toSet()
    }
}