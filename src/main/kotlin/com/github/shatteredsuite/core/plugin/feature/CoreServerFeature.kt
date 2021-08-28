package com.github.shatteredsuite.core.plugin.feature

import com.github.shatteredsuite.core.attribute.Identified
import com.github.shatteredsuite.core.data.plugin.PluginKey

data class CoreServerFeature(val key: PluginKey, val name: String, val description: String): Identified {
    override val id get() = key.toString()
}