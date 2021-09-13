package com.github.shatteredsuite.core.feature

import com.github.shatteredsuite.core.attribute.Identified
import com.github.shatteredsuite.core.data.plugin.PluginKey

class CoreFeature(
    val key: PluginKey,
    val name: String,
    val defaultEnabled: Boolean,
    val description: String,
    val permission: String,
    val enabled: Boolean = true,

    /**
     * Cooldown in milliseconds.
     */
    val cooldown: Int = 10
) : Identified {
    override val id: String
        get() = key.toString()
}