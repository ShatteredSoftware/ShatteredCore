package com.github.shatteredsuite.core.util

open class Manager<T : Identified> {
    private val registry: MutableMap<String, T> = mutableMapOf()

    fun get(id: String): T? {
        return registry[id]
    }

    fun getAll(): Iterable<T> {
        return registry.values
    }

    fun register(element: T) {
        registry[element.id] = element
    }
}