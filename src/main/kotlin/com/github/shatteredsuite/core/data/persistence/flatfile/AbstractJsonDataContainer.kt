package com.github.shatteredsuite.core.data.persistence.flatfile

import com.github.shatteredsuite.core.attribute.Identified
import com.github.shatteredsuite.core.data.persistence.CachedDataContainer
import com.google.gson.Gson
import java.io.File
import java.io.FileReader
import java.io.FileWriter


class AbstractJsonDataContainer<T : Identified>(
    private val dataFolder: File,
    private val cl: Class<T>,
    private val gson: Gson,
    prefetch: Boolean
) :
    CachedDataContainer<T>() {

    init {
        if (!dataFolder.exists()) {
            dataFolder.mkdirs()
        }
        if (prefetch) {
            dataFolder.listFiles { file -> file.endsWith(".json") }?.forEach { load(it.nameWithoutExtension) }
        }
    }

    override fun doLoad(id: String): T? {
        val file = File(dataFolder, "$id.json")
        if (!file.exists()) {
            return null
        }
        FileReader(file).use {
            return gson.fromJson(it, cl)
        }
    }

    override fun doSave(value: T) {
        val file = File(dataFolder, "${value.id}.json")
        val contents = gson.toJson(value)
        FileWriter(file).use {
            it.write(contents)
        }
    }

    override fun doDelete(id: String) {
        val file = File(dataFolder, "$id.json")
        if (file.exists()) {
            file.delete()
        }
    }
}