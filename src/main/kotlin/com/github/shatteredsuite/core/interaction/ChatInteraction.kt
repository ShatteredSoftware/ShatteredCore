package com.github.shatteredsuite.core.interaction

import com.github.shatteredsuite.core.data.player.CorePlayer
import com.github.shatteredsuite.core.plugin.listeners.ChatListener

abstract class ChatInteraction : Interaction {
    private val finalizers: MutableMap<String, () -> Unit> = mutableMapOf()

    protected abstract fun handleChat(player: CorePlayer, message: String)
    protected open fun onFinalize(player: CorePlayer) {}
    protected open fun onStart(player: CorePlayer) {}

    private fun handleMessage(player: CorePlayer, message: String) {
        if (message == "\\x") {
            finalize(player)
        }
        handleChat(player, message)
    }

    private fun getHandlerFor(player: CorePlayer): (String) -> Unit {
        return {
            handleMessage(player, it)
        }
    }

    override fun start(player: CorePlayer, finish: () -> Unit) {
        finalizers[player.id] = finish
        ChatListener.addCancelFor(player, "chatinteraction-$id:${player.id}", this.getHandlerFor(player))
        onStart(player)
    }

    override fun finalize(player: CorePlayer) {
        onFinalize(player)
        ChatListener.removeCancelFor(player, "chatinteraction-$id:${player.id}")
        finalizers[player.id]?.invoke()
    }
}