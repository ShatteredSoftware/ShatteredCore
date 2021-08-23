package com.github.shatteredsuite.core.data.player

import com.github.shatteredsuite.core.extension.addSafe

class UsernameCache {
    val usernames: MutableMap<String, MutableSet<String>> = mutableMapOf()

    fun add(player: CorePlayer) {
        usernames.addSafe(player.currentName, player.id)
    }
}