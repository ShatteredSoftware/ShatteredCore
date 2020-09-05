package com.github.shatteredsuite.core.tasks

import org.bukkit.plugin.java.JavaPlugin

class BukkitRunStrategy(private val plugin: JavaPlugin) : RunStrategy() {
    override fun run(runnable: Runnable) {
        plugin.server.scheduler.runTask(plugin, runnable)
    }
}