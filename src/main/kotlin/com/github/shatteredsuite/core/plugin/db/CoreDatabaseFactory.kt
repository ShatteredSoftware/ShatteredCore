package com.github.shatteredsuite.core.plugin.db

import com.github.shatteredsuite.core.sql.SchemaManager
import kotlin.IllegalArgumentException

object CoreDatabaseFactory {
    val schemaManagerInitializers = mutableMapOf<String, () -> SchemaManager>()
    val builtSchemaManagers = mutableMapOf<String, SchemaManager>()

    fun addSchemaManager(dialect: String, init: () -> SchemaManager) {
        schemaManagerInitializers[dialect] = init
    }

    fun getManager(dialect: String): SchemaManager {
        val builtManager = builtSchemaManagers[dialect]
        if (builtManager != null) {
            return builtManager
        }
        val init = schemaManagerInitializers[dialect]
        if (init != null) {
            val manager = init()
            builtSchemaManagers[dialect] = manager
            return manager
        }
        throw IllegalArgumentException("No managers for dialect $dialect.")
    }
}