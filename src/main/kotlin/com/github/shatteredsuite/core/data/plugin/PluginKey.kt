package com.github.shatteredsuite.core.data.plugin

import org.bukkit.NamespacedKey
import org.bukkit.plugin.Plugin
import java.util.*

open class PluginKey(val plugin: Plugin, val key: String = UUID.randomUUID().toString()) {
    override fun toString(): String {
        return "${plugin.name.lowercase()}:$key"
    }

    override fun equals(other: Any?): Boolean {
        if (other !is PluginKey || other !is NamespacedKey) {
            return false
        }
        return this === other || this.toString() == other.toString()
    }

    override fun hashCode(): Int {
        return toString().hashCode()
    }
}