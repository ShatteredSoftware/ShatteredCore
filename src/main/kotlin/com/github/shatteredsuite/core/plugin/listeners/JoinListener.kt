package com.github.shatteredsuite.core.plugin.listeners

import com.github.shatteredsuite.core.plugin.ShatteredCore
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

object JoinListener : Listener {

    @EventHandler
    fun onJoin(playerJoinEvent: PlayerJoinEvent) {
        ShatteredCore.instance.playerManager.join(playerJoinEvent.player)
    }
}