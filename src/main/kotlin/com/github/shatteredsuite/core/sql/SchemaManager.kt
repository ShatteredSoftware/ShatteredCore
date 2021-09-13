package com.github.shatteredsuite.core.sql

import com.github.shatteredsuite.core.data.plugin.PluginKey

interface SchemaManager {
    fun create()
    fun getCurrentVersion(key: PluginKey): Int
    fun setVersion(key: PluginKey, version: Int)
}