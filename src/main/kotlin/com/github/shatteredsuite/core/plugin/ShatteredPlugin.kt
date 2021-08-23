package com.github.shatteredsuite.core.plugin

import com.github.shatteredsuite.core.data.player.PlayerCooldownManager
import com.github.shatteredsuite.core.data.player.PlayerManager
import com.github.shatteredsuite.core.messages.Messageable
import com.github.shatteredsuite.core.messages.Messages
import com.github.shatteredsuite.core.messages.Messenger
import com.github.shatteredsuite.core.updates.UpdateChecker
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.bstats.bukkit.Metrics
import org.bukkit.Bukkit
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.event.Event
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.util.logging.Level

@Suppress("MemberVisibilityCanBePrivate")
abstract class ShatteredPlugin : JavaPlugin(), Messageable {
    protected var metrics: Metrics? = null

    protected open val bStatsId = 0
    protected open val spigotResourceId = 0
    protected open val requiresPaper: Boolean = false

    var isUpdateAvailable = false
        protected set
    private var loaded = false
    var latestVersion: String? = null
        private set
    protected var hasPaper = false

    protected lateinit var gson: Gson
    protected var core: ShatteredCore? = null
    private var messenger: Messenger? = null
    lateinit var playerManager: PlayerManager
    lateinit var featureCooldownManager: PlayerCooldownManager
    protected var hasPaper = false

    fun <T : Event> on(fn: (e: T) -> Unit) {
        this.server.pluginManager.registerEvents(object: Listener {
            @Suppress("unused")
            fun onEvent(e: T) {
                fn(e)
            }
        }, this)
    }

    /**
     * Do any work that must be done before loading the config.
     */
    protected fun preload() {}
    @Suppress("UNUSED_PARAMETER")
    protected fun gsonSetup(gsonBuilder: GsonBuilder) {}

    /**
     * Do any work to be done after loading configs. Register external connections here.
     *
     * @throws Exception Any error that occurs.
     */
    @Throws(Exception::class)
    protected open fun load() {
    }

    protected open fun postEnable() {}
    protected fun preDisable() {}
    protected fun onFirstTick() {}

    @Suppress("UNUSED_PARAMETER")
    protected fun parseConfig(config: YamlConfiguration?) {}

    fun loadConfig() {
        if (!dataFolder.exists()) {
            dataFolder.mkdirs()
        }
        val configFile = File(dataFolder, "config.yml")
        if (!configFile.exists()) {
            if (getResource("config.yml") != null) {
                saveResource("config.yml", false)
            }
        }
        parseConfig(YamlConfiguration.loadConfiguration(configFile))
    }

    override fun onLoad() {
        loaded = false
        try {
            initCore()
            checkPaper()
            initGson()

            playerManager = PlayerManager(this, this.gson)

            preload()
            loadMessages()
            loadConfig()
            load()

            loaded = true
        } catch (t: Throwable) {
            logger.log(Level.SEVERE, "An error occurred while loading.", t)
            loaded = false
        }
    }

    override fun onEnable() {
        if (!loaded) {
            logger.severe(
                "Plugin cannot be enabled due to an error that occurred during the plugin loading phase"
            )
            isEnabled = false
            return
        }
        if (bStatsId != 0) {
            metrics = Metrics(this, bStatsId)
        }
        if (spigotResourceId != 0) {
            val updateChecker = UpdateChecker(this, spigotResourceId)
            updateChecker.getVersion { version: String ->
                isUpdateAvailable = if (description.version.startsWith(version)) {
                    logger.info("You are up to date.")
                    false
                } else {
                    logger.info("Version $version is available.")
                    true
                }
                latestVersion = version
            }
        }

        on<PlayerJoinEvent> { playerManager.join(it.player) }
        on<PlayerQuitEvent> { playerManager.leave(it.player) }

        loadMessages()
        Bukkit.getScheduler().runTask(this, Runnable { onFirstTick() })
        postEnable()
    }

    override fun onDisable() {
        preDisable()
    }

    private fun initCore() {
        val corePlugin = getPlugin(ShatteredCore::class.java)
        if (!Bukkit.getPluginManager().isPluginEnabled(corePlugin)
            && !this.javaClass.isInstance(ShatteredCore::class.java)
        ) {
            logger.log(Level.SEVERE, "Could not find ShatteredCore. Disabling.")
            this.isEnabled = false
            this.loaded = false
            throw Exception("Could not find ShatteredCore. Disabling.")
        }
        core = corePlugin
        this.featureCooldownManager = PlayerCooldownManager(corePlugin.featureManager)
    }

    private fun checkPaper() {
        hasPaper = try {
            Class.forName("com.destroystokyo.paper.VersionHistoryManager\$VersionData")
            true
        }
        catch (ex: Exception) {
            false
        }

        if (requiresPaper && !hasPaper) {
            logger.log(Level.SEVERE, "This plugin requires Paper or a fork of Paper. Disabling.")
            this.isEnabled = false
            this.loaded = false
            throw Exception("This plugin requires Paper or a fork of Paper. Disabling.")
        }
    }

    private fun initGson() {
        val gsonBuilder = GsonBuilder().setPrettyPrinting().disableHtmlEscaping()
        gsonSetup(gsonBuilder)
        this.gson = gsonBuilder.create()
    }

    private fun loadMessages() {
        if (!dataFolder.exists()) {
            dataFolder.mkdirs()
        }
        val messagesFile = File(dataFolder, "messages.yml")
        if (!messagesFile.exists()) {
            saveResource("messages.yml", false)
        }
        val messages = Messages(this, YamlConfiguration.loadConfiguration(messagesFile))
        messenger = Messenger(this, messages)
    }

    override fun getMessenger(): Messenger {
        return messenger!!
    }
}