package com.github.shatteredsuite.core.bukkitdispatch.argument

import com.github.shatteredsuite.core.data.player.CorePlayer
import com.github.shatteredsuite.core.dispatch.argument.impl.primitive.ChoiceArgument
import com.github.shatteredsuite.core.bukkitdispatch.context.BukkitCommandContext
import com.github.shatteredsuite.core.plugin.CoreServer
import org.bukkit.Bukkit

open class OnlinePlayerArgument(name: String) : ChoiceArgument<BukkitCommandContext, CorePlayer>(
    name,
    { CoreServer.getPlayerByName(it) },
    { Bukkit.getOnlinePlayers().map { it.name } },
    "usage.player"
)