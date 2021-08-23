package com.github.shatteredsuite.core.data.player

import com.github.shatteredsuite.core.data.plugin.PluginKey
import com.github.shatteredsuite.core.extension.addSafe
import com.github.shatteredsuite.core.extension.getSafe
import com.github.shatteredsuite.core.plugin.tasks.RunStrategy

class CachedPlayerDataStore(val baseStore: PlayerDataStore, val runStrategy: RunStrategy) :
    PlayerDataStore {
    private val cache: MutableMap<String, MutableMap<String, Any>> = mutableMapOf()
    private val queued: MutableList<Pair<Pair<CorePlayer, PluginKey>, Any>> = mutableListOf()

    override fun <T : Any> save(player: CorePlayer, key: PluginKey, value: T) {
        cache.addSafe(key.key, player.id, value)
        queued.add(player to key to value)
    }

    override fun <T : Any> load(player: CorePlayer, key: PluginKey, clazz: Class<T>): T? {
        val value =
            cache.getSafe(key.key) { mutableMapOf() }[player.id] ?: baseStore.load(player, key, clazz) ?: return null
        if (clazz.isInstance(value)) {
            @Suppress("UNCHECKED_CAST")
            val checked = value as T
            cache.addSafe(key.key, player.id, checked)
            return checked
        }
        return null
    }

    fun flush() {
        runStrategy.execute {
            queued.forEach {
                val (pk, value) = it
                val (player, key) = pk
                baseStore.save(player, key, value)
            }
        }
    }
}