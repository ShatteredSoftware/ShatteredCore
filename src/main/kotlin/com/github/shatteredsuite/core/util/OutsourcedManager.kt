package com.github.shatteredsuite.core.util

import java.util.*

open class OutsourcedManager<T : Identified> : Manager<T>() {
    protected val externalSources: MutableMap<String, ExternalProvider<T>> = mutableMapOf()

    override fun get(id: String): T? {
        if (id.contains(':')) {
            val parts = id.split(Regex(":"), 2)
            val namespace = parts[0].lowercase()
            val name = parts[1]
            if (externalSources.containsKey(namespace)) {
                return externalSources[namespace]!!.get(name) ?: super.get(id)
            }
        }
        return super.get(id)
    }

    override fun has(id: String): Boolean {
        if (id.contains(':')) {
            val parts = id.split(Regex(":"), 2)
            val namespace = parts[0].lowercase()
            val name = parts[1]
            if (externalSources.containsKey(namespace)) {
                return externalSources[namespace]!!.has(name) || super.has(id)
            }
        }
        return super.has(id)
    }

    override fun getIds(): Iterable<String> {
        val results = mutableListOf<String>()
        results.addAll(super.getIds())
        for ((key, value) in externalSources.entries) {
            results.addAll(value.keys().map { "$key:$it" })
        }
        return results
    }

    override fun getAll(): Iterable<T> {
        val results = mutableListOf<T>()
        for (src in externalSources.values) {
            results.addAll(src.all())
        }
        results.addAll(super.getAll())
        return results
    }

    fun addSource(namespace: String, provider: ExternalProvider<T>) {
        this.externalSources[namespace.lowercase(Locale.getDefault())] = provider
    }
}

interface ExternalProvider<T> {
    fun get(id: String): T?
    fun has(id: String): Boolean
    fun keys(): Iterable<String>
    fun all(): Iterable<T> {
        val keys = keys()
        val results = mutableListOf<T>()
        for (key in keys) {
            val element = get(key) ?: continue
            results.add(element)
        }
        return results
    }
}