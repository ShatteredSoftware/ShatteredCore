package com.github.shatteredsuite.core.data.persistence.flatfile

import com.github.shatteredsuite.core.attribute.Identified
import com.github.shatteredsuite.core.data.persistence.CachedPlayerDataContainer
import com.github.shatteredsuite.core.data.plugin.PluginTypeKey
import com.github.shatteredsuite.core.plugin.tasks.RunStrategy
import com.google.gson.Gson
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class JsonPlayerDataBackend<T : Identified>(
    val key: PluginTypeKey<T>,
    private val gson: Gson,
    private val runStrategy: RunStrategy
) :
    CachedPlayerDataContainer<T>() {
    override fun doSave(id: String, value: T) {
        val baseFolder = File(this.key.plugin.dataFolder, "playerdata")
        baseFolder.mkdirs()

        val folder = File(baseFolder, id)
        folder.mkdirs()

        val file = File(folder, "${this.key.key}.json")

        runStrategy.execute {
            FileWriter(file).use {
                it.write(gson.toJson(value))
            }
        }
    }

    override fun doLoad(id: String): T? {
        val baseFolder = File(key.plugin.dataFolder, "playerdata")
        baseFolder.mkdirs()

        val folder = File(baseFolder, id)
        folder.mkdirs()

        val file = File(folder, "${key.key}.json")

        if (!file.exists()) {
            return null
        }

        FileReader(file).use {
            return gson.fromJson(it.readText(), key.clazz)
        }
    }

    override fun doDelete(id: String)  {
        val baseFolder = File(key.plugin.dataFolder, "playerdata")
        if (!baseFolder.exists()) {
            return
        }

        val folder = File(baseFolder, id)
        if (!folder.exists()) {
            return
        }

        val file = File(folder, "${key.key}.json")

        if (!file.exists()) {
            return
        }

        file.delete()
    }
}