package com.github.shatteredsuite.core.plugin.db

import com.github.shatteredsuite.core.data.plugin.PluginKey
import com.github.shatteredsuite.core.plugin.ShatteredCore
import com.github.shatteredsuite.core.plugin.config.CoreConfig
import com.github.shatteredsuite.core.sql.Migration
import com.github.shatteredsuite.core.sql.MigrationManager
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement

class CoreDatabase(val core: ShatteredCore, private val dialect: String) {
    private var migrationManager: MigrationManager = MigrationManager()
    private var dataSource: HikariDataSource? = null
    private var dataSourceEnabled = true
    private val schemaManager = CoreDatabaseFactory.getManager(dialect)
    private val safeDatasource get() = this.dataSource ?: throw IllegalStateException("Trying to do database operations on a disabled database")

    fun <R> withPossibleDatabase(function: Connection.() -> R): R? {
        val dataSource = dataSource
        val settings = ShatteredCore.config.storageSettings
        if (ShatteredCore.config.storageType == CoreConfig.StorageType.FLATFILE || !dataSourceEnabled || dataSource == null || settings == null) {
            return null
        }
        return function(dataSource.connection)
    }

    @Throws(IllegalStateException::class)
    fun <R> withDatabase(function: Connection.() -> R): R {
        val dataSource = dataSource
        val settings = ShatteredCore.config.storageSettings
        if (ShatteredCore.config.storageType == CoreConfig.StorageType.FLATFILE || !dataSourceEnabled || dataSource == null || settings == null) {
            throw IllegalStateException("Cannot run functions on a disabled database.")
        }
        return function(dataSource.connection)
    }

    fun checkSchema(key: PluginKey): Int {
        return schemaManager.getCurrentVersion(key)
    }

    fun migrate(key: PluginKey, migration: Migration) {
        if (key != migration.targetKey) {
            throw IllegalArgumentException("Invalid migration. Trying to use a migration for ${migration.targetKey} on $key.")
        }
        val version = checkSchema(key)
        if (version != migration.sourceVersion) {
            throw IllegalArgumentException("Invalid migration. Trying to use a migration for version ${migration.sourceVersion} on version $version.")
        }
        val success = migration.apply(safeDatasource.connection)
        if (success) {
            schemaManager.setVersion(key, migration.goalVersion)
        }
    }

    fun isSafe(): Boolean {
        return ShatteredCore.config.storageType != CoreConfig.StorageType.FLATFILE && dataSourceEnabled && dataSource != null && !(dataSource?.isClosed ?: true)
    }

    fun connect() {
        val coreConfig = ShatteredCore.config
        if (coreConfig.storageType == CoreConfig.StorageType.FLATFILE) {
            dataSourceEnabled = false
            return
        }
        val settings = ShatteredCore.config.storageSettings
        if (settings == null) {
            dataSourceEnabled = false
            return
        }
        val config = HikariConfig()
        config.jdbcUrl = "jdbc:mysql://${settings.host}:${settings.port}/${settings.database}"
        config.username = settings.username
        config.password = settings.password

        dataSource = HikariDataSource(config)
        schemaManager.create()
    }

    fun runQuery(query: String, init: Statement.() -> Unit): ResultSet {
        val statement = safeDatasource.connection.prepareStatement(query)
        init(statement)
        return statement.executeQuery()
    }

    fun disconnect() {
        dataSource?.close()
    }
}