package com.github.shatteredsuite.core.util

import com.github.shatteredsuite.core.ShatteredPlugin
import com.github.shatteredsuite.core.ext.get
import com.github.shatteredsuite.core.tasks.MainThreadRunStrategy
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import org.mockito.Mockito
import java.io.File
import java.util.*
import kotlin.random.Random.Default.nextInt


class TestPlayerDataManager {
    companion object {
        private lateinit var plugin: ShatteredPlugin
        @TempDir
        @JvmField
        var dataFolder: File = File("")
        private lateinit var playerDataManager: PlayerDataManager<TestPlayerData>
        private lateinit var uuid: UUID

        @BeforeAll
        @JvmStatic
        fun init() {
            plugin = Mockito.mock(ShatteredPlugin::class.java)
            Mockito.lenient().`when`(plugin.dataFolder).then {
                dataFolder
            }
            playerDataManager = PlayerDataManager(plugin, GsonBuilder().setPrettyPrinting().create(),
                TypeToken.get(TestPlayerData::class.java), runStrategy = MainThreadRunStrategy()) { TestPlayerData(it, "DefaultPlayer", 0)}
            for (child in dataFolder.listFiles() ?: arrayOf()) {
                child.delete()
            }
        }
    }

    @BeforeEach
    fun each() {
        uuid = UUID.randomUUID()
    }

    @Test
    fun `test saving`() {
        val playerNumber = nextInt(1, 100)
        playerDataManager.put(uuid.toString(), TestPlayerData(uuid.toString(), "Player$playerNumber", 5))
        assertThat(dataFolder["playerdata"]["$uuid.json"].exists(), `is`(true))
    }

    @Test
    fun `test loading`() {
        val playerNumber = nextInt(1, 100)
        playerDataManager.put(uuid.toString(), TestPlayerData(uuid.toString(), "Player$playerNumber", 5))
        val playerData = playerDataManager.get(uuid.toString())
        assertThat(playerData, notNullValue())
        assertThat(playerData.name, `is`("Player$playerNumber"))
    }

    @Test
    fun `test untrack`() {
        val playerNumber = nextInt(1, 100)
        playerDataManager.put(uuid.toString(), TestPlayerData(uuid.toString(), "Player$playerNumber", 5))
        playerDataManager.untrack(uuid.toString())
        val playerData = playerDataManager.get(uuid.toString())
        assertThat(playerData, notNullValue())
        assertThat(playerData.name, `is`("Player$playerNumber"))
    }
}