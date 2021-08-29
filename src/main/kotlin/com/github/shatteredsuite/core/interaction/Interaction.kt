package com.github.shatteredsuite.core.interaction

import com.github.shatteredsuite.core.attribute.Identified
import com.github.shatteredsuite.core.data.player.CorePlayer

interface Interaction : Identified {
    fun start(player: CorePlayer, finish: () -> Unit) {}
    fun finalize(player: CorePlayer) {}
}