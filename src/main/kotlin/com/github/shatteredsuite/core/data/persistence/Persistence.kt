package com.github.shatteredsuite.core.data.persistence

import com.github.shatteredsuite.core.attribute.Identified
import com.github.shatteredsuite.core.data.persistence.flatfile.AbstractJsonDataContainer
import com.github.shatteredsuite.core.data.persistence.flatfile.JsonPlayerDataBackend
import com.github.shatteredsuite.core.data.player.CorePlayer
import com.github.shatteredsuite.core.data.plugin.PluginTypeKey
import com.github.shatteredsuite.core.plugin.ShatteredCore
import com.github.shatteredsuite.core.plugin.tasks.RunStrategy
import com.github.shatteredsuite.core.extension.merge
import com.google.gson.Gson
import java.io.File

object Persistence {
    private val containerManager: DataContainerManager = DataContainerManager()
    private val playerDataContainerManager: PlayerDataContainerManager = PlayerDataContainerManager()

    // Data

    fun <T : Identified> addDataContainer(pluginKey: PluginTypeKey<T>, dataContainer: DataContainer<T>) {
        containerManager.addDataContainer(pluginKey, dataContainer)
    }

    fun <T : Identified> save(pluginTypeKey: PluginTypeKey<T>, value: T) {
        containerManager.save(pluginTypeKey, value)
    }

    fun <T : Identified> load(pluginTypeKey: PluginTypeKey<T>, id: String): T? {
        return containerManager.load(pluginTypeKey, id)
    }

    fun <T : Identified> delete(pluginTypeKey: PluginTypeKey<T>, id: String) {
        containerManager.delete(pluginTypeKey, id)
    }

    fun <T : Identified> newJsonBackend(pluginTypeKey: PluginTypeKey<T>, gson: Gson = ShatteredCore.defaultGson, prefetch: Boolean = false) {
        containerManager.addDataContainer(pluginTypeKey, AbstractJsonDataContainer(pluginTypeKey.plugin.dataFolder, pluginTypeKey.clazz, gson, prefetch))
    }

    // PlayerData

    fun <T : Identified> addPlayerDataContainer(pluginTypeKey: PluginTypeKey<T>, container: PlayerDataContainer<T>) {
        playerDataContainerManager.addDataContainer(pluginTypeKey, container)
    }

    fun <T : Identified> save(player: CorePlayer, pluginTypeKey: PluginTypeKey<T>, value: T) {
        playerDataContainerManager.save(player, pluginTypeKey, value)
    }

    fun <T : Identified> load(player: CorePlayer, pluginTypeKey: PluginTypeKey<T>): T? {
        return playerDataContainerManager.load(player, pluginTypeKey)
    }

    fun <T : Identified> load(player: CorePlayer, pluginTypeKey: PluginTypeKey<T>, init: () -> T): T {
        return playerDataContainerManager.load(player, pluginTypeKey, init)
    }

    fun <T : Identified> delete(player: CorePlayer, pluginTypeKey: PluginTypeKey<T>) {
        playerDataContainerManager.delete(player, pluginTypeKey)
    }

    fun <T : Identified> newPlayerJsonBackend(pluginTypeKey: PluginTypeKey<T>, gson: Gson = ShatteredCore.defaultGson, runStrategy: RunStrategy = ShatteredCore.defaultRunStrategy) {
        playerDataContainerManager.addDataContainer(pluginTypeKey, JsonPlayerDataBackend(pluginTypeKey, gson, runStrategy))
    }

    // Generic JSON File Manipulation

    fun <T> loadPluginJsonFileAs(pluginTypeKey: PluginTypeKey<T>, gson: Gson? = null, init: (() -> T)): T {
        return if (gson != null) {
            FileUtil.loadPluginJsonFileAs(pluginTypeKey, gson, init)
        }
        else FileUtil.loadPluginJsonFileAs(pluginTypeKey, init = init)
    }

    fun <T> loadPluginJsonFileAs(pluginTypeKey: PluginTypeKey<T>, gson: Gson? = null): T? {
        return if (gson != null) {
            FileUtil.loadPluginJsonFileAs(pluginTypeKey, gson)
        }
        else FileUtil.loadPluginJsonFileAs(pluginTypeKey)
    }

    inline fun <reified T> loadJsonFileAs(file: File, gson: Gson? = null, noinline init: (() -> T)): T {
        return loadJsonFileAs(file, T::class.java, gson, init)
    }

    fun <T> loadJsonFileAs(file: File, clazz: Class<T>, gson: Gson? = null, init: (() -> T)): T {
        return if (gson != null) {
            FileUtil.loadJsonFileAs(file, clazz, gson, init)
        }
        else FileUtil.loadJsonFileAs(file, clazz, init = init)
    }

    inline fun <reified T> loadJsonFileAs(file: File, gson: Gson? = null): T? {
        return loadJsonFileAs(file, T::class.java, gson)
    }

    fun <T> loadJsonFileAs(file: File, clazz: Class<T>, gson: Gson? = null): T? {
        return if (gson != null) {
            FileUtil.loadJsonFileAs(file, clazz, gson)
        }
        else FileUtil.loadJsonFileAs(file, clazz)
    }

    fun <T> saveJsonFileAs(file: File, value: T, gson: Gson? = null, runStrategy: RunStrategy? = null) {
        if (gson != null && runStrategy != null) {
            FileUtil.saveJsonFileAs(file, value, gson, runStrategy)
        }
        else if (gson != null) {
            FileUtil.saveJsonFileAs(file, value, gson)
        }
        else if (runStrategy != null) {
            FileUtil.saveJsonFileAs(file, value, runStrategy = runStrategy)
        }
        else {
            FileUtil.saveJsonFileAs(file, value)
        }
    }

    fun <T> savePluginJsonFileAs(pluginTypeKey: PluginTypeKey<T>, value: T, gson: Gson? = null, runStrategy: RunStrategy? = null) {
        if (gson != null && runStrategy != null) {
            FileUtil.savePluginJsonFileAs(pluginTypeKey, value, gson, runStrategy)
        }
        else if (gson != null) {
            FileUtil.savePluginJsonFileAs(pluginTypeKey, value, gson)
        }
        else if (runStrategy != null) {
            FileUtil.savePluginJsonFileAs(pluginTypeKey, value, runStrategy = runStrategy)
        }
        else {
            FileUtil.savePluginJsonFileAs(pluginTypeKey, value)
        }
    }

    // Generic YAML File Manipulation

    inline fun <reified T> loadYamlFileAs(file: File, gson: Gson? = null, noinline init: () -> T): T {
        return loadYamlFileAs(file, T::class.java, gson, init)
    }

    fun <T> loadYamlFileAs(file: File, clazz: Class<T>, gson: Gson? = null, init: (() -> T)): T {
        return if (gson != null) {
            FileUtil.loadYamlFileAs(file, clazz, gson, init)
        }
        else FileUtil.loadYamlFileAs(file, clazz, init = init)
    }

    inline fun <reified T> loadYamlFileAs(file: File, gson: Gson? = null): T? {
        return loadYamlFileAs(file, T::class.java, gson)
    }

    fun <T> loadYamlFileAs(file: File, clazz: Class<T>, gson: Gson? = null): T? {
        return if (gson != null) {
            FileUtil.loadYamlFileAs(file, clazz, gson)
        }
        else FileUtil.loadYamlFileAs(file, clazz)
    }

    fun <T> loadPluginYamlFileAs(pluginTypeKey: PluginTypeKey<T>, gson: Gson? = null, init: () -> T): T {
        return if (gson != null) {
            FileUtil.loadPluginYamlFileAs(pluginTypeKey, gson, init)
        }
        else FileUtil.loadPluginYamlFileAs(pluginTypeKey, init = init)
    }

    fun <T> loadPluginYamlFileAs(pluginTypeKey: PluginTypeKey<T>, gson: Gson? = null): T? {
        return if (gson != null) {
            FileUtil.loadPluginYamlFileAs(pluginTypeKey, gson)
        }
        else FileUtil.loadPluginYamlFileAs(pluginTypeKey)
    }

    fun <T> savePluginYamlFileAs(pluginTypeKey: PluginTypeKey<T>, value: T, runStrategy: RunStrategy? = null) {
        if (runStrategy != null) {
            FileUtil.savePluginYamlFileAs(pluginTypeKey, value, runStrategy)
        }
        else {
            FileUtil.savePluginYamlFileAs(pluginTypeKey, value)
        }
    }

    fun <T> saveYamlFileAs(file: File, value: T, runStrategy: RunStrategy? = null) {
        if (runStrategy != null) {
            FileUtil.saveYamlFileAs(file, value, runStrategy)
        }
        else {
            FileUtil.saveYamlFileAs(file, value)
        }
    }
}