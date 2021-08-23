package com.github.shatteredsuite.core.plugin

import com.github.shatteredsuite.core.config.ConfigRecipe
import com.github.shatteredsuite.core.data.item.trait.Quality
import com.github.shatteredsuite.core.data.item.trait.Rarity
import com.github.shatteredsuite.core.data.persistence.DataContainer
import com.github.shatteredsuite.core.data.persistence.DataContainerManager
import com.github.shatteredsuite.core.data.persistence.flatfile.AbstractJsonDataContainer
import com.github.shatteredsuite.core.data.player.*
import com.github.shatteredsuite.core.feature.CoreFeatureManager
import com.github.shatteredsuite.core.plugin.tasks.AsyncBukkitRunStrategy
import com.github.shatteredsuite.core.util.PlayerDataManager
import org.bukkit.configuration.serialization.ConfigurationSerialization
import java.io.File

class ShatteredCore : ShatteredPlugin() {
    override var bStatsId = 7496
    val featureManager: CoreFeatureManager = CoreFeatureManager()

    override fun load() {
        ConfigurationSerialization.registerClass(ConfigRecipe::class.java)
        CoreServer.setInstance(this)

    }

    override fun postEnable() {
        getCommand("shatteredcore")!!.setExecutor(AboutCommand(this))
    }
}