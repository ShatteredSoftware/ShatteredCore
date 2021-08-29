package com.github.shatteredsuite.core.plugin.listeners

import com.github.shatteredsuite.core.plugin.ShatteredCore
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

object LeaveListener : Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        ShatteredCore.instance.playerManager.leave(event.player)
    }
}