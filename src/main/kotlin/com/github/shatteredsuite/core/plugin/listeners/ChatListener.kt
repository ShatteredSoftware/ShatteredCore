package com.github.shatteredsuite.core.plugin.listeners

import com.github.shatteredsuite.core.data.player.CorePlayer
import io.papermc.paper.event.player.AsyncChatEvent
import io.papermc.paper.text.PaperComponents
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

object ChatListener : Listener {
    private val listeners: MutableMap<String, (message: String) -> Unit> = mutableMapOf()
    private val playerListeners: MutableMap<String, String> = mutableMapOf()
    private val cancelFor: MutableMap<String, Int> = mutableMapOf()

    fun addCancelFor(player: CorePlayer, id: String, forward: (message: String) -> Unit) {
        cancelFor[player.id] = (cancelFor[player.id] ?: 0) + 1
        listeners[id] = forward
        playerListeners[player.id] = id
    }

    fun removeCancelFor(player: CorePlayer, id: String) {
        cancelFor[player.id] = (cancelFor[player.id] ?: 1) - 1
        listeners.remove(id)
        if ((cancelFor[player.id] ?: 0) < 1) {
            cancelFor.remove(player.id)
            playerListeners.remove(player.id)
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    fun onChatEvent(event: AsyncChatEvent) {
        val id = event.player.uniqueId.toString()
        if (cancelFor.containsKey(id)) {
            event.isCancelled = true
            val listener = listeners[playerListeners[id]] ?: return
            listener.invoke(PaperComponents.plainSerializer().serialize(event.message()))
        }
    }
}