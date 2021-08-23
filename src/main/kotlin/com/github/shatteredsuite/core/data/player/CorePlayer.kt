package com.github.shatteredsuite.core.data.player

import com.github.shatteredsuite.core.attribute.Identified
import com.github.shatteredsuite.core.data.persistence.GenericDataStore
import org.bukkit.entity.Player

class CorePlayer(@Transient val internalPlayer: Player) : Identified, Player by internalPlayer {
    override val id = internalPlayer.uniqueId.toString()
    val currentName = internalPlayer.name
    val knownUsernames = setOf(internalPlayer.name)
    @Transient
    val data = GenericDataStore()
}