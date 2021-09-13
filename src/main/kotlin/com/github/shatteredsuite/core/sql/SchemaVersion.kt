package com.github.shatteredsuite.core.sql

import com.github.shatteredsuite.core.data.plugin.PluginKey

data class SchemaVersion(val key: PluginKey, val version: Int)