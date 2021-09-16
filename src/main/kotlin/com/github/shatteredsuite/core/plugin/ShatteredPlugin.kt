package com.github.shatteredsuite.core.plugin

import com.github.shatteredsuite.core.data.player.PlayerCooldownManager
import com.github.shatteredsuite.core.data.player.PlayerManager
import com.github.shatteredsuite.core.data.plugin.PluginKey
import com.github.shatteredsuite.core.dispatch.command.DispatchCommand
import com.github.shatteredsuite.core.dispatch.command.DispatchCommandBuilder
import com.github.shatteredsuite.core.dispatch.context.BukkitCommandContext
import com.github.shatteredsuite.core.message.MessageProcessorStore
import com.github.shatteredsuite.core.message.lang.MessageSet
import com.github.shatteredsuite.core.messages.Messageable
import com.github.shatteredsuite.core.messages.Messages
import com.github.shatteredsuite.core.messages.Messenger
import com.github.shatteredsuite.core.plugin.feature.ShatteredModule
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
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.nio.file.Files
import java.util.*
import java.util.logging.Level
import java.util.stream.Collectors
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import kotlin.Comparator

@Suppress("MemberVisibilityCanBePrivate")
abstract class ShatteredPlugin(val childClass: Class<out ShatteredPlugin>) : JavaPlugin(), Messageable {
    protected var metrics: Metrics? = null

    protected open val bStatsId = 0
    protected open val spigotResourceId = 0
    protected open val requiresPaper: Boolean = false

    var isUpdateAvailable = false
        protected set

    var latestVersion: String? = null
        private set

    var isCore: Boolean = false
        private set

    protected var hasPaper = false
    private var loaded = false

    protected open val module = ShatteredModule(this.name)

    protected open val requiredFeatureIds: Set<String> = setOf()

    protected lateinit var gson: Gson

    protected var core: ShatteredCore? = null
    private var messenger: Messenger? = null

    lateinit var playerManager: PlayerManager
    lateinit var featureCooldownManager: PlayerCooldownManager

    private val internalMessageSet: MessageSet by lazy { MessageSet() }
    val messageProcessorStore = MessageProcessorStore()
    val messageSet: MessageSet by lazy { if (isCore) this.internalMessageSet else core!!.messageSet }

    protected fun <T : Event> on(fn: (e: T) -> Unit) {
        this.server.pluginManager.registerEvents(object : Listener {
            @Suppress("unused")
            fun onEvent(e: T) {
                fn(e)
            }
        }, this)
    }

    protected fun checkPlugin(pluginName: String): Boolean {
        return server.pluginManager.isPluginEnabled(pluginName)
    }

    protected inline fun <reified T : Plugin> checkPluginThen(pluginName: String, fn: T.() -> Unit) {
        val plugin = server.pluginManager.getPlugin(pluginName)
        if (plugin == null || plugin !is T || !plugin.isEnabled) {
            return
        }
        fn(plugin)
    }

    /**
     * Do any work that must be done before loading the config.
     */
    protected fun preload() {
    }

    @Suppress("UNUSED_PARAMETER")
    protected fun gsonSetup(gsonBuilder: GsonBuilder) {
    }

    /**
     * Do any work to be done after loading configs. Register external connections here.
     *
     * @throws Exception Any error that occurs.
     */
    @Throws(Exception::class)
    protected open fun load() {
    }

    protected open fun postEnable() {}
    protected open fun preDisable() {}
    protected open fun onFirstTick() {}

    @Suppress("UNUSED_PARAMETER")
    protected fun parseConfig(config: YamlConfiguration?) {
    }

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
            extractResources(childClass)
            loadMessageSet()
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
            bigScaryMessage {
                logger.severe(
                    "${description.name} cannot be enabled due to an error that"
                )
                logger.severe("occurred during the plugin loading phase.")
            }
            isEnabled = false
            return
        }
        if(!featuresOk()) {
            this.isEnabled = false
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

        Bukkit.getScheduler().runTask(this, Runnable { onFirstTick() })
        postEnable()
    }

    override fun onDisable() {
        preDisable()
    }

    private fun initCore() {
        val corePlugin = getPlugin(ShatteredCore::class.java)
        isCore = childClass.javaClass.isInstance(ShatteredCore::class.java)
        if (!Bukkit.getPluginManager().isPluginEnabled(corePlugin) && !isCore) {
            bigScaryMessage {
                logger.severe("Could not find ShatteredCore. This plugin requires ShatteredCore.")
            }
            this.isEnabled = false
            this.loaded = false
            throw Exception("Could not find ShatteredCore. Disabling.")
        }
        register(corePlugin)
    }

    private fun featuresOk(): Boolean {
        val failures = mutableListOf<String>()
        for (feature in requiredFeatureIds) {
            if (!this.core!!.hasFeature(feature)) {
                failures.add(feature)
            }
        }
        if (failures.isNotEmpty()) {
            bigScaryMessage {
                logger.severe("Could not load the following required features:")
                logger.severe("")
                for (failure in failures) {
                    logger.severe(" - $failure")
                }
                logger.severe("")
                logger.severe("This likely indicates a missing or out-of-date plugin.")
            }
            return false
        }
        return true
    }

    private fun register(coreRef: ShatteredCore) {
        core = coreRef
        this.featureCooldownManager = PlayerCooldownManager(coreRef.featureManager)
        coreRef.register(this.module)
    }

    private fun checkPaper() {
        hasPaper = try {
            Class.forName("com.destroystokyo.paper.VersionHistoryManager\$VersionData")
            true
        } catch (ex: Exception) {
            false
        }

        if (requiresPaper && !hasPaper) {
            bigScaryMessage {
                logger.severe("This plugin requires Paper or a fork of Paper. We are not able to find Paper. Disabling."
                )
                logger.severe("If you are using a fork of Paper, please contact the plugin author.")
            }
            this.isEnabled = false
            this.loaded = false
            throw Exception("This plugin requires Paper or a fork of Paper. Disabling.")
        }
    }

    private fun bigScaryMessage(f: () -> Unit) {
        logger.severe("============================================================")
        logger.severe("")
        logger.severe("")
        f()
        logger.severe("")
        logger.severe("")
        logger.severe("Get help:")
        logger.severe("https://discord.gg/zUbNX9t")
        logger.severe("")
        logger.severe("")
        logger.severe("============================================================")
    }

    private fun initGson() {
        val gsonBuilder = GsonBuilder().setPrettyPrinting().disableHtmlEscaping()
        gsonSetup(gsonBuilder)
        this.gson = gsonBuilder.create()
    }

    private fun extractResources(
        cl: Class<out ShatteredPlugin>,
        exclude: Set<String> = setOf("plugin.yml"),
        prefix: String = "unpack/"
    ) {
        if (!dataFolder.exists()) {
            dataFolder.mkdirs()
        }

        val jar = ZipFile(cl.protectionDomain.codeSource.location.path)
        val entries = jar.stream()
            .filter { f -> f.name.startsWith(prefix) }
            .filter { f -> !exclude.any { f.name.endsWith(it) } }
            .sorted(Comparator.comparing(ZipEntry::getName))
            .collect(Collectors.toList())

        for (entry in entries) {
            val dest = dataFolder.resolve(entry.name.substring(prefix.length))
            if (dest.exists()) {
                continue
            }
            if (entry.isDirectory) {
                Files.createDirectory(dest.toPath())
                continue
            }
            Files.copy(jar.getInputStream(entry), dest.toPath())
        }
    }

    private fun loadMessages() {
        if (!dataFolder.exists()) {
            dataFolder.mkdirs()
        }
        val messagesFile = File(dataFolder, "unpack/messages.yml")
        if (!messagesFile.exists()) {
            saveResource("unpack/messages.yml", false)
        }
        val yaml = YamlConfiguration.loadConfiguration(messagesFile)
        val messages = Messages(this, yaml)
        messenger = Messenger(this, messages)
    }

    private fun loadMessageSet() {
        val langFolder = File(dataFolder, "locale")
        langFolder.listFiles()?.forEach {
            if (it.extension == "yml" || it.extension == "yaml") {
                val language = it.nameWithoutExtension.split("-", limit=2)[0]
                val locale = Locale(language)
                logger.info("Loaded locale information for $language (${locale.displayName})")
                messageSet.fromYaml(YamlConfiguration.loadConfiguration(it), locale)
            }
        }
    }

    override fun getMessenger(): Messenger {
        return messenger!!
    }

    protected fun command(key: PluginKey, fn: DispatchCommandBuilder<BukkitCommandContext>.() -> Unit) {
        DispatchCommand.build(key.toString(), fn)
    }

    fun key(value: String): PluginKey {
        return PluginKey(this, value)
    }
}