package com.github.shatteredsuite.core.data.persistence

import com.github.shatteredsuite.core.attribute.Identified
import com.github.shatteredsuite.core.plugin.api.CoreCRUDAPI

interface DataContainer<T : Identified> : CoreCRUDAPI<T> {
    fun getIds(): Set<String>
    fun invalidate() {}
    fun flush() {}
}