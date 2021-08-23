package com.github.shatteredsuite.core.plugin

import com.github.shatteredsuite.core.data.player.CorePlayer
import com.github.shatteredsuite.core.data.plugin.PluginKey
import com.github.shatteredsuite.core.data.plugin.PluginTypeKey
import com.github.shatteredsuite.core.data.region.CoreRegion
import com.github.shatteredsuite.core.feature.CoreFeatureManager

object CoreServer {
    private lateinit var core: ShatteredCore

    fun setInstance(core: ShatteredCore) {
        this.core = core
    }

    fun getPlayer(id: String): CorePlayer? {
        return core.playerManager.onlinePlayers[id]
    }

    fun getPlayerByName(name: String): CorePlayer? {
        return core.playerManager.onlinePlayerNames[name]
    }

    fun <T : Any> getPlayerData(player: CorePlayer, key: PluginKey, clazz: Class<T>): T? {
        return core.playerManager.playerDataManager.load(player, key, clazz)
    }

    fun <T : Any> getPlayerData(player: CorePlayer, key: PluginTypeKey<T>) : T? {
        return getPlayerData(player, key, key.clazz)
    }

    fun <T : Any> savePlayerData(player: CorePlayer, key: PluginKey, value: T) {
        core.playerManager.playerDataManager.save(player, key, value)
    }

    fun <T : Any> savePlayerData(player: CorePlayer, key: PluginTypeKey<T>) {
        core.playerManager.playerDataManager.save(player, key, key.clazz)
    }

    fun getFeatureManager(): CoreFeatureManager {
        return core.featureManager
    }
}