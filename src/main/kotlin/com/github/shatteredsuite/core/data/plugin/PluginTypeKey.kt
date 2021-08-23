package com.github.shatteredsuite.core.data.plugin

import org.bukkit.plugin.Plugin

open class PluginTypeKey<T>(plugin: Plugin, val clazz: Class<T>, key: String) : PluginKey(plugin, key) {
    override fun toString(): String {
        return "$plugin:${clazz.name}:$key"
    }

    override fun equals(other: Any?): Boolean {
        if (other !is PluginTypeKey<*>) {
            return false
        }
        return this === other || this.toString() == other.toString()
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + clazz.hashCode()
        return result
    }
}