package com.github.shatteredsuite.core.plugin.listeners

import com.github.shatteredsuite.core.plugin.ShatteredCore
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class LeaveListener(val plugin: ShatteredCore) : Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        plugin
    }
}