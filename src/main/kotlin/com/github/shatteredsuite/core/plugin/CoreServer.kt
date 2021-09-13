package com.github.shatteredsuite.core.plugin

import com.github.shatteredsuite.core.data.player.CorePlayer
import com.github.shatteredsuite.core.data.plugin.PluginKey
import com.github.shatteredsuite.core.data.plugin.PluginTypeKey
import com.github.shatteredsuite.core.feature.CoreFeatureManager
import java.util.*

object CoreServer {
    lateinit var core: ShatteredCore

    fun setInstance(core: ShatteredCore) {
        this.core = core
    }

    fun getPlayer(id: String): CorePlayer? {
        return core.playerManager.onlinePlayers[id]
    }

    fun getPlayerByName(name: String): CorePlayer? {
        return core.playerManager.onlinePlayerNames[name]
    }

    fun getFeatureManager(): CoreFeatureManager {
        return core.featureManager
    }

    fun getLocale(player: CorePlayer) : Locale {
        // FIXME
        return Locale.US
    }
}