package com.github.shatteredsuite.core.data.persistence

import com.github.shatteredsuite.core.attribute.Identified
import com.github.shatteredsuite.core.data.player.CorePlayer
import com.github.shatteredsuite.core.data.plugin.PluginTypeKey

class PlayerDataContainerManager {
    private val dataContainers: MutableMap<PluginTypeKey<*>, PlayerDataContainer<*>> = mutableMapOf()

    fun <T : Identified> addDataContainer(pluginKey: PluginTypeKey<T>, dataContainer: PlayerDataContainer<T>) {
        dataContainers[pluginKey] = dataContainer
    }

    private fun <T : Identified> getContainer(pluginTypeKey: PluginTypeKey<T>): PlayerDataContainer<T>? {
        @Suppress("UNCHECKED_CAST") // I promise they match
        return dataContainers[pluginTypeKey] as PlayerDataContainer<T>?
    }

    fun <T : Identified> save(player: CorePlayer, pluginTypeKey: PluginTypeKey<T>, value: T) {
        val container = getContainer(pluginTypeKey) ?: return
        container.save(player, value)
    }

    fun <T : Identified> load(player: CorePlayer, pluginTypeKey: PluginTypeKey<T>): T? {
        val container = getContainer(pluginTypeKey) ?: return null
        val result = container.load(player) ?: return null
        if (pluginTypeKey.clazz.isInstance(result)) {
            return result
        }
        return null
    }

    fun <T : Identified> load(player: CorePlayer, pluginTypeKey: PluginTypeKey<T>, init: () -> T): T {
        val container = getContainer(pluginTypeKey) ?: return init()
        val result = container.load(player, init)
        if (pluginTypeKey.clazz.isInstance(result)) {
            return result
        }
        return init()
    }

    fun <T : Identified> delete(player: CorePlayer, pluginTypeKey: PluginTypeKey<T>) {
        val container = getContainer(pluginTypeKey) ?: return
        container.delete(player)
    }
}
