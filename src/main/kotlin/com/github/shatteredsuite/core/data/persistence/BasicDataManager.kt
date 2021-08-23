package com.github.shatteredsuite.core.data.persistence

import com.github.shatteredsuite.core.attribute.Identified

class BasicDataManager : DataManager {
    private val classMap: MutableMap<String, Class<out Identified>> = mutableMapOf()
    private val storeMap: MutableMap<String, DataContainer<out Identified>> = mutableMapOf()
    private val reverseMap: MutableMap<String, String> = mutableMapOf()

    override fun <T : Identified> addStore(name: String, store: DataContainer<T>, clazz: Class<T>) {
        storeMap[name] = store
        classMap[name] = clazz
        reverseMap[clazz.name] = name
    }

    override fun <T : Identified> set(name: String, value: T) {
        val (clazz, store) = this.getClassStore<T>(name)
        if (!clazz.isInstance(value)) {
            throw IllegalArgumentException("Cannot store value of type ${value::class.java.name} in a container for ${clazz.name}")
        }
        store.save(value)
    }

    override fun <T : Identified> get(name: String, id: String): T? {
        val (_, store) = this.getClassStore<T>(name)
        return store.load(id)
    }

    override fun <T : Identified> get(name: String, id: String, default: T, push: Boolean): T {
        val (_, store) = this.getClassStore<T>(name)
        val res = store.load(id)
        if (res != null) {
            return res
        }
        if (push) {
            set(name, default)
        }
        return default
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : Identified> getClassStore(name: String): Pair<Class<T>, DataContainer<T>> {
        if (!classMap.containsKey(name)) {
            throw IllegalArgumentException("$name is not a valid store type: no valid class")
        }
        if (!storeMap.containsKey(name)) {
            throw IllegalArgumentException("$name is not a valid store type: no store found")
        }
        val clazz = classMap[name]!! as Class<T>
        val store = storeMap[name]!! as DataContainer<T>
        return clazz to store
    }
}