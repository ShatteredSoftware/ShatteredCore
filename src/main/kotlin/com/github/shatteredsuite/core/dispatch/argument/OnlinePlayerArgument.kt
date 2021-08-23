package com.github.shatteredsuite.core.dispatch.argument

import com.github.shatteredsuite.core.data.player.CorePlayer
import com.github.shatteredsuite.core.plugin.CoreServer
import org.bukkit.Bukkit

open class OnlinePlayerArgument(name: String) : ChoiceArgument<CorePlayer>(
    name,
    { CoreServer.getPlayerByName(it) },
    { Bukkit.getOnlinePlayers().map { it.name } })