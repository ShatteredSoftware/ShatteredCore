package com.github.shatteredsuite.core.data.persistence

fun interface MapAdapter<T> {
    fun adapt(map: Map<String, Any>): T
}