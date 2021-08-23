package com.github.shatteredsuite.core.data.persistence

import com.github.shatteredsuite.core.attribute.Identified


abstract class CachedDataContainer<T : Identified> : DataContainer<T> {
    protected val cache = mutableMapOf<String, T>()
    protected val deleted = mutableListOf<String>()

    abstract fun doLoad(id: String): T?

    abstract fun doDelete(id: String)

    abstract fun doSave(value: T)

    override fun getIds(): Set<String> {
        return cache.keys
    }

    override fun load(id: String): T? {
        if (cache.containsKey(id)) {
            return cache[id]
        }
        val value = doLoad(id)
        if (value != null) {
            cache[id] = value
        }
        return value
    }

    override fun save(value: T) {
        cache[value.id] = value
    }

    override fun delete(id: String) {
        cache.remove(id)
        deleted.add(id)
    }

    override fun flush() {
        cache.values.forEach {
            doSave(it)
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