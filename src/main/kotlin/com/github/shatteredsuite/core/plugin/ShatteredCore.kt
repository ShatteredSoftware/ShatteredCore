package com.github.shatteredsuite.core.plugin

import com.github.shatteredsuite.core.config.ConfigRecipe
import com.github.shatteredsuite.core.data.persistence.Persistence
import com.github.shatteredsuite.core.data.plugin.PluginKey
import com.github.shatteredsuite.core.data.plugin.PluginTypeKey
import com.github.shatteredsuite.core.extension.merge
import com.github.shatteredsuite.core.feature.CoreFeatureManager
import com.github.shatteredsuite.core.plugin.config.CoreConfig
import com.github.shatteredsuite.core.plugin.feature.CoreServerFeature
import com.github.shatteredsuite.core.plugin.feature.ShatteredModule
import com.github.shatteredsuite.core.plugin.listeners.ChatListener
import com.github.shatteredsuite.core.plugin.listeners.JoinListener
import com.github.shatteredsuite.core.plugin.listeners.LeaveListener
import com.github.shatteredsuite.core.plugin.tasks.AsyncBukkitRunStrategy
import com.github.shatteredsuite.core.plugin.tasks.MainThreadRunStrategy
import com.github.shatteredsuite.core.plugin.tasks.RunStrategy
import com.google.gson.Gson
import org.bukkit.configuration.serialization.ConfigurationSerialization
import java.util.*
import javax.script.ScriptEngineManager

class ShatteredCore : ShatteredPlugin(ShatteredCore::class.java) {
    companion object {
        var bStatsId = 7496
            private set

        var defaultGson = Gson()
            private set

        var defaultRunStrategy: RunStrategy = MainThreadRunStrategy()
            private set

        var config: CoreConfig = CoreConfig("en", true, CoreConfig.StorageType.FLATFILE, null)
            private set

        private var internalInstance: ShatteredCore? = null

        val instance: ShatteredCore get() {
            return internalInstance ?: throw IllegalStateException("Using Core before it was initialized.")
        }

        var defaultLocale: Locale = Locale.US
            private set
    }


    override val bStatsId: Int = ShatteredCore.bStatsId
    val featureManager: CoreFeatureManager = CoreFeatureManager()
    private val availableFeatures: MutableSet<CoreServerFeature> = mutableSetOf()
    private val availableFeatureIds: MutableSet<String> = mutableSetOf()

    val messengerKey = PluginKey(this, "messenger")
    val messageSetKey = PluginKey(this, "message_set")
    val coreServerKey = PluginKey(this, "coreserver")
    val persistenceKey = PluginKey(this, "persistence")

    val configTypeKey = PluginTypeKey(this, CoreConfig::class.java, "config")

    init {
        CoreServer.setInstance(this)

        this.module.addFeature(
            CoreServerFeature(
                messengerKey,
                "Internationalization v1 (Messenger)",
                "A simple interface for translation."
            )
        )
        this.module.addFeature(
            CoreServerFeature(
                messageSetKey,
                "Internationalization v2 (Message Set)",
                "An alternative, smarter interface for translation."
            )
        )
        this.module.addFeature(
            CoreServerFeature(
                coreServerKey,
                "CoreServer",
                "A collection of basic operations on an easy-to-use interface."
            )
        )
        this.module.addFeature(
            CoreServerFeature(
                persistenceKey,
                "Persistence",
                "A collection of utilities to assist in storing and loading data."
            )
        )
    }

    override fun load() {
        ConfigurationSerialization.registerClass(ConfigRecipe::class.java)
        defaultGson = this.gson
        defaultRunStrategy = AsyncBukkitRunStrategy(this)
        internalInstance = this
        ShatteredCore.config =
            Persistence.loadPluginYamlFileAs(configTypeKey, init = this::initConfig)
        defaultLocale = Locale(ShatteredCore.config.defaultLocale)
    }

    private fun initConfig(): CoreConfig {
        return CoreConfig("en", true, CoreConfig.StorageType.FLATFILE, null) merge initConfig()
    }


    override fun postEnable() {
        getCommand("shatteredcore")!!.setExecutor(AboutCommand(this))

        if (ShatteredCore.config.listFeatures) {
            logger.info("The following features are available:")
            for (feature in availableFeatures) {
                logger.info(" - §a${feature.name}§f: §7${feature.description}")
            }
        }

        server.pluginManager.registerEvents(ChatListener, this)
        server.pluginManager.registerEvents(JoinListener, this)
        server.pluginManager.registerEvents(LeaveListener, this)
    }

    override fun preDisable() {
        Persistence.savePluginYamlFileAs(configTypeKey, ShatteredCore.config, gson, MainThreadRunStrategy())
    }

    fun hasFeature(name: String): Boolean {
        return availableFeatureIds.contains(name)
    }

    fun hasFeature(feature: CoreServerFeature): Boolean {
        return availableFeatures.contains(feature)
    }

    fun register(module: ShatteredModule) {
        module.getFeatures().forEach {
            availableFeatures.add(it)
            availableFeatureIds.add(it.id)
        }
    }
}