package com.github.shatteredsuite.core.interaction

import com.github.shatteredsuite.core.data.player.CorePlayer
import com.github.shatteredsuite.core.plugin.CoreServer

object InteractionManager {
    private val interactions = mutableMapOf<String, Interaction>()

    fun isInInteraction(player: CorePlayer): Boolean {
        return interactions[player.id] != null
    }

    fun getCurrentInteraction(player: CorePlayer): Interaction? {
        return interactions[player.id]
    }

    fun startInteraction(player: CorePlayer, interaction: Interaction) {
        interactions[player.id] = interaction
        println("Starting interaction ${interaction.id} for ${player.id}")
        interaction.start(player) {
            val updatedPlayer = CoreServer.getPlayer(player.id) ?: return@start
            finishInteraction(player)
        }
    }

    fun finishInteraction(player: CorePlayer) {
        val interaction = interactions[player.id] ?: return
        println("Ending interaction ${interaction.id} for ${player.id}")
        interaction.finalize(player)
    }
}