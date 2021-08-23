package com.github.shatteredsuite.core.data.persistence

import com.github.shatteredsuite.core.attribute.Identified

interface DataManager {
    fun <T : Identified> addStore(name: String, store: DataContainer<T>, clazz: Class<T>)

    fun <T : Identified> set(name: String, value: T)

    fun <T : Identified> get(name: String, id: String): T?

    fun <T : Identified> get(name: String, id: String, default: T, push: Boolean): T
}