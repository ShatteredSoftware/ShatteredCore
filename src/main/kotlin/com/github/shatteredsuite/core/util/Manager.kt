package com.github.shatteredsuite.core.util

import java.util.*

open class Manager<T : Identified> : Iterable<T> {
    protected val registry: MutableMap<String, T> = mutableMapOf()

    open fun has(id: String): Boolean {
        return registry.containsKey(id.lowercase(Locale.getDefault()))
    }

    open fun get(id: String): T? {
        return registry[id.lowercase(Locale.getDefault())]
    }

    open fun getAll(): Iterable<T> {
        return this
    }

    open fun getIds(): Iterable<String> {
        return registry.keys
    }

    open fun register(element: T) {
        registry[element.id.lowercase(Locale.getDefault())] = element
    }

    open fun delete(element: T) {
        registry.remove(element.id.lowercase(Locale.getDefault()))
    }

    open fun delete(id: String) {
        registry.remove(id.lowercase(Locale.getDefault()))
    }

    override fun iterator(): Iterator<T> {
        return registry.values.iterator()
    }

    open fun clear() {
        registry.clear()
    }
}