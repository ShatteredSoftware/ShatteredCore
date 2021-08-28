package com.github.shatteredsuite.core.data.persistence

import com.github.shatteredsuite.core.data.plugin.PluginTypeKey
import com.github.shatteredsuite.core.plugin.ShatteredCore
import com.github.shatteredsuite.core.plugin.tasks.RunStrategy
import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.JsonSyntaxException
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.io.FileReader
import java.io.FileWriter

object FileUtil {
    private val defaultGson: Gson get() = ShatteredCore.defaultGson
    private val defaultRunStrategy: RunStrategy get() = ShatteredCore.defaultRunStrategy
    private val yaml = Yaml()

    fun <T> loadPluginJsonFileAs(pluginTypeKey: PluginTypeKey<T>, gson: Gson = defaultGson): T? {
        val file = File(pluginTypeKey.plugin.dataFolder, pluginTypeKey.key)
        return loadJsonFileAs(file, pluginTypeKey.clazz, gson)
    }
    fun <T> loadPluginJsonFileAs(pluginTypeKey: PluginTypeKey<T>, gson: Gson = defaultGson, init: (() -> T)): T {
        val file = File(pluginTypeKey.plugin.dataFolder, pluginTypeKey.key)
        return loadJsonFileAs(file, pluginTypeKey.clazz, gson, init)
    }

    fun <T> loadJsonFileAs(file: File, clazz: Class<T>, gson: Gson = defaultGson): T? {
        FileReader(file).use {
            return try {
                gson.fromJson(it, clazz)
            } catch (ex: Exception) {
                null
            }
        }
    }

    fun <T> loadJsonFileAs(file: File, clazz: Class<T>, gson: Gson = defaultGson, init: (() -> T)): T {
        FileReader(file).use {
            return try {
                gson.fromJson(it, clazz)
            }
            catch (ex: Exception) {
                init.invoke()
            }
        }
    }

    fun <T> savePluginJsonFileAs(pluginTypeKey: PluginTypeKey<T>, value: T, gson: Gson = defaultGson, runStrategy: RunStrategy = defaultRunStrategy) {
        val file = File(pluginTypeKey.plugin.dataFolder, pluginTypeKey.key + ".json")
        saveJsonFileAs(file, value, gson, runStrategy)
    }

    fun <T> saveJsonFileAs(file: File, value: T, gson: Gson = defaultGson, runStrategy: RunStrategy = defaultRunStrategy) {
        runStrategy.execute {
            if (!file.parentFile.exists()) {
                file.parentFile.mkdirs()
            }
            val json = gson.toJson(value)
            FileWriter(file).use {
                it.write(json)
            }
        }
    }

    fun <T> loadPluginYamlFileAs(pluginTypeKey: PluginTypeKey<T>, gson: Gson = defaultGson, init: (() -> T)): T {
        val file = File(pluginTypeKey.plugin.dataFolder, pluginTypeKey.key)
        return loadYamlFileAs(file, pluginTypeKey.clazz, gson, init)
    }

    fun <T> loadPluginYamlFileAs(pluginTypeKey: PluginTypeKey<T>, gson: Gson = defaultGson): T? {
        val file = File(pluginTypeKey.plugin.dataFolder, pluginTypeKey.key)
        return loadYamlFileAs(file, pluginTypeKey.clazz, gson)
    }

    fun <T> loadYamlFileAs(file: File, clazz: Class<T>, gson: Gson = defaultGson, init: (() -> T)): T {
        FileReader(file).use {
            val contents: Any = yaml.load(it)
            val json = gson.toJson(contents, LinkedHashMap::class.java)
            return try {
                gson.fromJson(json, clazz)
            } catch (ex: Exception) {
                init.invoke()
            }
        }
    }

    fun <T> loadYamlFileAs(file: File, clazz: Class<T>, gson: Gson = defaultGson): T? {
        FileReader(file).use {
            val contents: Any = yaml.load(it)
            val json = gson.toJson(contents, LinkedHashMap::class.java)
            try {
                return gson.fromJson(json, clazz)
            }
            catch (ex: Exception) {
                return null
            }
        }
    }

    fun <T> savePluginYamlFileAs(pluginTypeKey: PluginTypeKey<T>, value: T, runStrategy: RunStrategy = defaultRunStrategy) {
        val file = File(pluginTypeKey.plugin.dataFolder, pluginTypeKey.key + ".yml")
        saveYamlFileAs(file, value, runStrategy)
    }

    fun <T> saveYamlFileAs(file: File, value: T, runStrategy: RunStrategy = defaultRunStrategy) {
        runStrategy.execute {
            if (!file.parentFile.exists()) {
                file.parentFile.mkdirs()
            }
            FileWriter(file).use {
                yaml.dump(value, it)
            }
        }
    }
}