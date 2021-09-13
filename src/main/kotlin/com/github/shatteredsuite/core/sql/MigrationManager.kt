package com.github.shatteredsuite.core.sql

import com.github.shatteredsuite.core.data.plugin.PluginKey
import com.github.shatteredsuite.core.extension.getSafe
import java.util.*

class MigrationManager {
    private val schemas: MutableMap<String, MutableMap<Int, PriorityQueue<Migration>>> = mutableMapOf()

    fun getBestMigration(key: PluginKey, currentVersion: Int): Migration? {
        return schemas[key.toString()]?.get(currentVersion)?.peek()
    }

    fun addMigration(key: PluginKey, migration: Migration) {
        val schemaMap = schemas.getSafe(key.toString()) { mutableMapOf() }
        val queue = schemaMap.getSafe(migration.sourceVersion) { PriorityQueue(Comparator.comparingInt<Migration?> { it.goalVersion }.reversed()) }
        queue.add(migration)
    }
}