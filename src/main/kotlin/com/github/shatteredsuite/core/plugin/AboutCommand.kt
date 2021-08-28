package com.github.shatteredsuite.core.plugin

import com.github.shatteredsuite.core.commands.LeafCommand
import com.github.shatteredsuite.core.commands.predicates.CommandContext
import org.bukkit.plugin.PluginDescriptionFile

class AboutCommand(private val instance: ShatteredCore) :
    LeafCommand(instance, null, "shatteredcore", "shatteredcore.command.about", null) {
    val description = instance.description

    override fun execute(context: CommandContext) {
        val updateStatus: String =
            if (instance.isUpdateAvailable) {
                instance.messenger.getMessage("update-available", mapOf("version" to instance.latestVersion))
            } else {
                instance.messenger.getMessage("up-to-date", null)
            }

        val authors: String = getAuthors(description)

        context.messenger.sendMessage(
            context.sender,
            "about",
            mapOf("version" to instance.description.version, "update-status" to updateStatus, "authors" to authors)
        )
    }

    fun getAuthors(description: PluginDescriptionFile): String {
        return when {
            description.authors.size > 2 -> {
                instance.messenger.getMessage("author-first-format", mapOf("author" to description.authors[0])) +
                        description.authors.slice(1 until description.authors.lastIndex).joinToString {
                            instance.messenger.getMessage("author-format", mapOf("author" to it))
                        } +
                        instance.messenger.getMessage(
                            "author-format-last",
                            mapOf("author" to description.authors.last())
                        )
            }
            description.authors.size == 2 -> {
                instance.messenger.getMessage("author-first-format", mapOf("author" to description.authors[0])) +
                        instance.messenger.getMessage(
                            "author-format-last",
                            mapOf("author" to description.authors.last())
                        )
            }
            description.authors.size == 1 -> {
                instance.messenger.getMessage("author-last-format", mapOf("author" to description.authors[0]))
            }
            else -> instance.messenger.getMessage("author-last-format", mapOf("author" to "Anonymous"))
        }
    }
}