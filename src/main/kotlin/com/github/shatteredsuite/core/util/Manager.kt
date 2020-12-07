package com.github.shatteredsuite.core.util

open class Manager<T : Identified> : Iterable<T> {
    protected val registry: MutableMap<String, T> = mutableMapOf()

    open fun has(id: String): Boolean {
        return registry.containsKey(id.toLowerCase())
    }

    open fun get(id: String): T? {
        return registry[id.toLowerCase()]
    }

    open fun getAll(): Iterable<T> {
        return this
    }

    open fun getIds() : Iterable<String> {
        return registry.keys
    }

    open fun register(element: T) {
        registry[element.id.toLowerCase()] = element
    }

    open fun delete(element: T) {
        registry.remove(element.id.toLowerCase())
    }

    open fun delete(id: String) {
        registry.remove(id.toLowerCase())
    }

    override fun iterator(): Iterator<T> {
        return registry.values.iterator()
    }

    open fun clear() {
        registry.clear()
    }
}