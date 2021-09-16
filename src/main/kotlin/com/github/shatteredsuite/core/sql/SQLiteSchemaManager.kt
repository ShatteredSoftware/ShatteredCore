package com.github.shatteredsuite.core.sql

import com.github.shatteredsuite.core.data.plugin.PluginKey
import com.github.shatteredsuite.core.plugin.ShatteredCore
import com.github.shatteredsuite.core.plugin.config.StorageSettings

class SQLiteSchemaManager(private val core: ShatteredCore, settings: StorageSettings) : SchemaManager {
    private val tableName = settings.prefix + "_schema_version"
    private val createStatement = """
        CREATE TABLE IF NOT EXISTS $tableName (
        id INT NOT NULL AUTO_INCREMENT,
        title VARCHAR(255) NOT NULL,
        version INT NOT NULL,
        PRIMARY KEY ( id ));
    """.trimIndent()

    private val getStatement = """
        SELECT * FROM $tableName WHERE title=%1;
    """.trimIndent()

    private val setStatement = """
        UPDATE $tableName WHERE title=%1 SET version=%2;
    """.trimIndent()

    override fun create() {
        core.withPossibleDatabase {
            prepareStatement(createStatement).execute()
        }
    }


    override fun getCurrentVersion(key: PluginKey): Int {
        return core.withPossibleDatabase {
            val statement = prepareStatement(getStatement)
            statement.setObject(1, key.toString())
            val result = statement.executeQuery()
            result.getInt("version")
        } ?: 0
    }

    override fun setVersion(key: PluginKey, version: Int) {
        core.withPossibleDatabase {
            val statement = prepareStatement(setStatement)
            statement.setString(1, key.toString())
            statement.setInt(2, version)
            statement.execute()
        }
    }
}