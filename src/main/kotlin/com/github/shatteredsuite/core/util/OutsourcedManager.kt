package com.github.shatteredsuite.core.util

open class OutsourcedManager<T : Identified> : Manager<T>() {
    protected val externalSources: MutableMap<String, ExternalProvider<T>> = mutableMapOf()

    override fun get(id: String): T? {
        if(id.contains(':')) {
            val parts = id.split(Regex(":"), 1)
            val namespace = parts[0]
            val name = parts[1]
            if(externalSources.containsKey(namespace)) {
                return externalSources[namespace]?.get(name) ?: return super.get(id)
            }
        }
        return super.get(id)
    }

    override fun has(id: String): Boolean {
        if(id.contains(':')) {
            val parts = id.split(Regex(":"), 1)
            val namespace = parts[0]
            val name = parts[1]
            if(externalSources.containsKey(namespace)) {
                return externalSources[namespace]?.has(name) ?: return super.has(id)
            }
        }
        return super.has(id)
    }

    override fun getIds() : Iterable<String> {
        val results = mutableListOf<String>()
        results.addAll(super.getIds())
        for((key, value) in externalSources.entries) {
            results.addAll(value.keys().map { "$key:$it" })
        }
        return results
    }

    override fun getAll(): Iterable<T> {
        val results = mutableListOf<T>()
        for(src in externalSources.values) {
            results.addAll(src.all())
        }
        results.addAll(super.getAll())
        return results
    }

    fun addSource(namespace: String, provider: ExternalProvider<T>) {
        this.externalSources[namespace] = provider
    }
}

interface ExternalProvider<T> {
    fun get(id: String): T?
    fun has(id: String): Boolean
    fun all(): Iterable<T>
    fun keys(): Iterable<String>
}