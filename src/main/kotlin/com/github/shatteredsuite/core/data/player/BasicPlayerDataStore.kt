package com.github.shatteredsuite.core.data.player

import com.github.shatteredsuite.core.data.plugin.PluginKey
import com.github.shatteredsuite.core.plugin.tasks.RunStrategy
import com.google.gson.Gson
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class BasicPlayerDataStore(val gson: Gson, val runStrategy: RunStrategy) : PlayerDataStore {
    override fun <T : Any> save(player: CorePlayer, key: PluginKey, value: T) {
        val baseFolder = File(key.plugin.dataFolder, "playerdata")
        baseFolder.mkdirs()

        val folder = File(baseFolder, player.id)
        folder.mkdirs()

        val file = File(folder, "${key.key}.json")

        runStrategy.execute {
            FileWriter(file).use {
                it.write(gson.toJson(value))
            }
        }
    }

    override fun <T : Any> load(player: CorePlayer, key: PluginKey, clazz: Class<T>): T? {
        val baseFolder = File(key.plugin.dataFolder, "playerdata")
        baseFolder.mkdirs()

        val folder = File(baseFolder, player.id)
        folder.mkdirs()

        val file = File(folder, "${key.key}.json")

        if (!file.exists()) {
            return null
        }

        FileReader(file).use {
            return gson.fromJson(it.readText(), clazz)
        }
    }
}