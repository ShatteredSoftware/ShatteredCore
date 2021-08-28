package com.github.shatteredsuite.core.data.player

import com.github.shatteredsuite.core.attribute.Identified
import com.github.shatteredsuite.core.data.persistence.Persistence
import com.github.shatteredsuite.core.data.plugin.PluginTypeKey
import com.github.shatteredsuite.core.plugin.ShatteredPlugin
import com.github.shatteredsuite.core.plugin.tasks.AsyncBukkitRunStrategy
import com.google.gson.Gson
import org.bukkit.entity.Player

class PlayerManager(
    val plugin: ShatteredPlugin,
    val gson: Gson,
    private val prefetch: Set<PluginTypeKey<Identified>> = setOf()
) {
    val onlinePlayers: MutableMap<String, CorePlayer> = mutableMapOf()
    val onlinePlayerNames: MutableMap<String, CorePlayer> = mutableMapOf()

    fun join(player: Player) {
        val corePlayer = CorePlayer(player)
        onlinePlayers[player.uniqueId.toString()] = corePlayer
        onlinePlayerNames[player.name] = corePlayer
        prefetch.forEach {
            val data = Persistence.load(corePlayer, it) ?: return@forEach
            corePlayer.data[it.toString()] = data
        }
    }

    fun leave(player: Player) {
        val corePlayer = onlinePlayers[player.uniqueId.toString()] ?: return
        onlinePlayers.remove(player.uniqueId.toString())
        onlinePlayerNames.remove(player.name)
        prefetch.forEach {
            val data = corePlayer.data.get(it.toString(), it.clazz) ?: return@forEach
            Persistence.save(corePlayer, it, data)
        }
    }
}