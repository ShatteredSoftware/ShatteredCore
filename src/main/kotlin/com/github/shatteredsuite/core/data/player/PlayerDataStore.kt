package com.github.shatteredsuite.core.data.player

import com.github.shatteredsuite.core.data.plugin.PluginKey

interface PlayerDataStore {
    fun <T : Any> save(player: CorePlayer, key: PluginKey, value: T)
    fun <T : Any> load(player: CorePlayer, key: PluginKey, clazz: Class<T>): T?
    fun <T : Any> load(player: CorePlayer, key: PluginKey, clazz: Class<T>, init: () -> T): T {
        return load(player, key, clazz) ?: init()
    }
}