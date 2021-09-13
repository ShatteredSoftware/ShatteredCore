package com.github.shatteredsuite.core.dispatch.context

import com.github.shatteredsuite.core.attribute.CommandSenderHolder
import com.github.shatteredsuite.core.attribute.FeatureUser
import com.github.shatteredsuite.core.data.player.CorePlayer
import com.github.shatteredsuite.core.feature.CoreFeature
import com.github.shatteredsuite.core.message.MessageProcessorStore
import com.github.shatteredsuite.core.plugin.CoreServer
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import java.util.*

class BukkitCommandContext(override val sender: CommandSender, messageProcessorStore: MessageProcessorStore): CommandContext(messageProcessorStore), CommandSenderHolder, FeatureUser {
    override fun canUse(feature: CoreFeature): Boolean {
        if (sender is Player) {
            return CoreServer.getFeatureManager().canUse(CoreServer.getPlayer(sender.uniqueId.toString()) ?: return false, feature)
        }
        if (sender is ConsoleCommandSender) {
            return feature.enabled
        }
        return false
    }

    override fun sendFailureMessage(message: String) {
        sender.sendMessage(message)
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