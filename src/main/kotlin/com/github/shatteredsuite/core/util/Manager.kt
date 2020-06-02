package com.github.shatteredsuite.core.util

open class Manager<T : Identified> {
    protected val registry: MutableMap<String, T> = mutableMapOf()

    open fun get(id: String): T? {
        return registry[id]
    }

    open fun getAll(): Iterable<T> {
        return registry.values
    }

    open fun register(element: T) {
        registry[element.id] = element
    }
}