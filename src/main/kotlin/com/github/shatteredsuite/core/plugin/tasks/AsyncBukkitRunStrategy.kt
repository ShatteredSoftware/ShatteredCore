package com.github.shatteredsuite.core.plugin.tasks

import org.bukkit.plugin.java.JavaPlugin

class AsyncBukkitRunStrategy(private val plugin: JavaPlugin) : RunStrategy() {
    override fun execute(runnable: Runnable) {
        plugin.server.scheduler.runTaskAsynchronously(plugin, runnable)
    }
}