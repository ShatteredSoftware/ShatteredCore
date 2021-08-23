package com.github.shatteredsuite.core.plugin.api

import com.github.shatteredsuite.core.attribute.Identified

interface CoreCRUDAPI<T : Identified> {
    fun load(id: String): T?
    fun save(value: T)
    fun delete(id: String)
}