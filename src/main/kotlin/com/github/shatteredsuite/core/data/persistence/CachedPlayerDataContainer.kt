package com.github.shatteredsuite.core.data.persistence

import com.github.shatteredsuite.core.attribute.Identified
import com.github.shatteredsuite.core.data.player.CorePlayer

abstract class CachedPlayerDataContainer<T : Identified> : PlayerDataContainer<T> {
    protected val cache = mutableMapOf<String, T>()
    protected val deleted = mutableListOf<String>()

    abstract fun doLoad(id: String): T?

    abstract fun doDelete(id: String)

    abstract fun doSave(id: String, value: T)

    override fun load(player: CorePlayer): T? {
        val id = player.id
        if (cache.containsKey(id)) {
            return cache[id]
        }
        val value = doLoad(id)
        if (value != null) {
            cache[id] = value
        }
        return value
    }

    override fun save(player: CorePlayer, value: T) {
        val id = player.id
        cache[id] = value
    }

    override fun delete(player: CorePlayer) {
        val id = player.id
        cache.remove(id)
        deleted.add(id)
    }

    override fun flush() {
        cache.entries.forEach { (key, it) ->
            doSave(key, it)
        }
        deleted.forEach {
            doDelete(it)
        }
        deleted.clear()
    }

    override fun invalidate() {
        flush()
        cache.clear()
    }
}