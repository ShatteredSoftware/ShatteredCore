package com.github.shatteredsuite.core.data.persistence

import com.github.shatteredsuite.core.attribute.Identified
import com.github.shatteredsuite.core.data.plugin.PluginTypeKey

class DataContainerManager {
    val containers: MutableMap<PluginTypeKey<*>, DataContainer<*>> = mutableMapOf()

    fun <T : Identified> addContainer(pluginKey: PluginTypeKey<T>, dataContainer: DataContainer<T>) {
        containers[pluginKey] = dataContainer
    }

    fun <T : Identified> getStore(pluginTypeKey: PluginTypeKey<T>): DataContainer<T>? {
        @Suppress("UNCHECKED_CAST") // I promise they match
        return containers[pluginTypeKey] as DataContainer<T>?
    }

    fun <T : Identified> save(pluginTypeKey: PluginTypeKey<T>, value: T) {
        val container = getStore(pluginTypeKey) ?: return
        container.save(value)
    }

    fun <T : Identified> load(pluginTypeKey: PluginTypeKey<T>, id: String): T? {
        val container = getStore(pluginTypeKey) ?: return null
        val result = container.load(id) ?: return null
        if (pluginTypeKey.clazz.isInstance(result)) {
            return result
        }
        return null
    }

    fun <T : Identified> delete(pluginTypeKey: PluginTypeKey<T>, id: String) {
        val container = getStore(pluginTypeKey) ?: return
        container.delete(id)
    }
}