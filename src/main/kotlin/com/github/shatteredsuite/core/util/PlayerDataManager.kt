package com.github.shatteredsuite.core.util

import com.github.shatteredsuite.core.ShatteredPlugin
import com.github.shatteredsuite.core.ext.get
import com.github.shatteredsuite.core.tasks.AsyncBukkitRunStrategy
import com.github.shatteredsuite.core.tasks.RunStrategy
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException

class PlayerDataManager<T>(
    private val plugin: ShatteredPlugin, private val gson: Gson, private val typeToken: TypeToken<T>,
    name: String = "playerdata", runStrategy: RunStrategy? = null
) {

    private val dataFolder: File
    private val playerData: MutableMap<String, T> = mutableMapOf()
    private val runStrategy: RunStrategy

    init {
        val file = File(plugin.dataFolder, name)
        if (!file.exists()) {
            file.mkdirs()
        }
        if (!file.isDirectory) {
            throw IOException("Player data folder is not a directory.")
        }
        dataFolder = file
        this.runStrategy = runStrategy ?: AsyncBukkitRunStrategy(plugin)
    }

    /**
     * Loads (if untracked) and returns a player's data.
     *
     * @see load
     */
    fun get(id: String): T {
        return if (id in playerData) {
            playerData[id]!!
        }
        else pull(id)
    }

    /**
     * Updates a player's data and saves it to their file asynchronously.
     */
    fun put(id: String, data: T) {
        playerData[id] = data
        runStrategy.run(Runnable {
            writeFile(id, data)
        })
    }

    /**
     * Loads a player's file without returning the data.
     *
     * @see get
     */
    fun load(id: String) {
        if (id !in playerData) {
            playerData[id] = pull(id)
        }
    }

    /**
     * Saves all tracked players' data to the disk asynchronously.
     *
     * @see untrackAll
     */
    fun saveAll() {
        runStrategy.run(Runnable { Unit
            for ((id, data) in playerData.entries) {
                writeFile(id, data)
            }
        })
    }

    /**
     * Saves all tracked players' data to the disk based on the RunStrategy. Removes players from tracking.
     *
     * @see saveAll
     */
    fun untrackAll() {
        runStrategy.run(Runnable { Unit
            for (id in playerData.keys) {
                untrack(id)
            }
        })
    }

    fun untrack(id: String) {
        if (id in playerData) {
            writeFile(id, playerData[id]!!)
            playerData.remove(id)
        }
    }

    private fun pull(id: String): T {
        val file = getFile(id)
        if (!file.exists()) {
            writeFile(id)
        }
        val reader = FileReader(file)
        val data = gson.fromJson<T>(reader, typeToken.type)
        reader.close()
        playerData[id] = data
        return data
    }

    private fun getFile(id: String): File {
        return dataFolder["$id.json"]
    }

    private fun writeFile(id: String, data: T? = null) {
        val filePath = getFile(id)
        if (!filePath.exists()) {
            File(filePath.parent).mkdirs()
            filePath.createNewFile()
        }
        val contents = gson.toJson(data ?: JsonObject())
        val writer = FileWriter(filePath)
        writer.write(contents)
        writer.close()
    }

}

