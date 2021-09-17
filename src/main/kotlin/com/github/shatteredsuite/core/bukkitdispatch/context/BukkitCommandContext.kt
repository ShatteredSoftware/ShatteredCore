package com.github.shatteredsuite.core.bukkitdispatch.context

import com.github.shatteredsuite.core.data.player.CorePlayer
import com.github.shatteredsuite.core.dispatch.context.CommandContext
import com.github.shatteredsuite.core.feature.CoreFeature
import com.github.shatteredsuite.core.message.MessageProcessorStore
import com.github.shatteredsuite.core.plugin.CoreServer
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer
import net.md_5.bungee.api.chat.BaseComponent
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import java.util.*

class BukkitCommandContext(val sender: CommandSender, messageProcessorStore: MessageProcessorStore, private val miniMessage: MiniMessage): CommandContext(messageProcessorStore) {
    companion object {
        private val serializer: BungeeComponentSerializer = BungeeComponentSerializer.get()
    }

    fun canUse(feature: CoreFeature): Boolean {
        if (sender is Player) {
            return CoreServer.getFeatureManager().canUse(CoreServer.getPlayer(sender.uniqueId.toString()) ?: return false, feature)
        }
        if (sender is ConsoleCommandSender) {
            return feature.enabled
        }
        return false
    }

    override fun sendMessage(message: String) {
        val components: Array<out BaseComponent> = serializer.serialize(miniMessage.parse(message))
        sender.sendMessage(*components)
    }

    override fun getLocale(): Locale {
        return CoreServer.getLocale(getPlayer() ?: return Locale.US)
    }

    fun getPlayer() : CorePlayer? {
        return if (sender is Player) {
            CoreServer.getPlayer(sender.uniqueId.toString())
        }
        else null
    }
}