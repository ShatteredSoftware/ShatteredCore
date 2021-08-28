package com.github.shatteredsuite.core.data.generic

class MaskedDataContainer<T : Any>(private val cl: Class<T>, private val dataStore: GenericDataStore) {
    operator fun get(id: String): T? {
        return dataStore.get(id, cl)
    }

    operator fun set(id: String, value: T) {
        dataStore.put(id, value)
    }

    fun unmasked(): GenericDataStore {
        return dataStore
    }
}