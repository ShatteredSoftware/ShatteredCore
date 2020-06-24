package com.github.shatteredsuite.core.util

open class Manager<T : Identified> : Iterable<T> {
    protected val registry: MutableMap<String, T> = mutableMapOf()

    open fun get(id: String): T? {
        return registry[id]
    }

    open fun getAll(): Iterable<T> {
        return this
    }

    open fun register(element: T) {
        registry[element.id] = element
    }

    open fun delete(element: T) {
        registry.remove(element.id)
    }

    open fun delete(id: String) {
        registry.remove(id)
    }

    override fun iterator(): Iterator<T> {
        return registry.values.iterator()
    }
}