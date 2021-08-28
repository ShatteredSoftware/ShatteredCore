package com.github.shatteredsuite.core.data.persistence

import com.github.shatteredsuite.core.attribute.Identified
import com.github.shatteredsuite.core.data.player.CorePlayer
import com.github.shatteredsuite.core.extension.tee

interface PlayerDataContainer<T : Identified> {
    fun init() {}
    fun save(player: CorePlayer, value: T)
    fun load(player: CorePlayer): T?
    fun delete(player: CorePlayer)
    fun load(player: CorePlayer, init: () -> T): T {
        return load(player).let { result ->
            if (result != null) return result
            else init().tee {
                save(player, it)
            }
        }
    }

    fun flush() {}
    fun invalidate() {}
}