package com.github.shatteredsuite.core.sql

import com.github.shatteredsuite.core.data.plugin.PluginKey
import java.sql.Connection

interface Migration {
    val sourceVersion: Int
    val goalVersion: Int
    val targetKey: PluginKey
    fun apply(connection: Connection): Boolean
}