package com.github.shatteredsuite.core.plugin

import com.github.shatteredsuite.core.commands.WrappedCommand
import org.bukkit.command.CommandSender

class AboutCommand(private val instance: ShatteredCore) :
    WrappedCommand(instance, null, "shatteredcore", "shatteredcore.command.about", null) {
    override fun onCommand(sender: CommandSender, label: String, args: Array<String>): Boolean {
        if (!showHelpOrNoPerms(sender, label, args)) {
            return true
        }
        val updateStatus: String =
        if (instance.isUpdateAvailable) {
            instance.messenger.getMessage("update-available", mapOf("version" to instance.latestVersion))
        } else {
            instance.messenger.getMessage("up-to-date", null)
        }
        instance.messenger.sendMessage(sender, "about", mapOf("version" to instance.description.version, "update-status" to updateStatus))
        return true
    }
}