package com.github.shatteredsuite.core.plugin

import com.github.shatteredsuite.core.commands.LeafCommand
import com.github.shatteredsuite.core.commands.predicates.CommandContext

class AboutCommand(private val instance: ShatteredCore) :
    LeafCommand(instance, null, "shatteredcore", "shatteredcore.command.about", null) {
    override fun execute(context: CommandContext) {
        val updateStatus: String =
            if (instance.isUpdateAvailable) {
                instance.messenger.getMessage("update-available", mapOf("version" to instance.latestVersion))
            } else {
                instance.messenger.getMessage("up-to-date", null)
            }
        context.messenger.sendMessage(
            context.sender,
            "about",
            mapOf("version" to instance.description.version, "update-status" to updateStatus)
        )
    }
}