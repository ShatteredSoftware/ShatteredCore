package com.github.shatteredsuite.core.tasks

import org.bukkit.plugin.java.JavaPlugin

class AsyncBukkitRunStrategy(private val plugin: JavaPlugin) : RunStrategy() {
    override fun run(runnable: Runnable) {
        plugin.server.scheduler.runTaskAsynchronously(plugin, runnable)
    }
}