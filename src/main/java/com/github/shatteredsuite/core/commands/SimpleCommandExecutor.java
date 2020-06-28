package com.github.shatteredsuite.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public abstract class SimpleCommandExecutor implements CommandExecutor {

    public abstract boolean onCommand(CommandSender sender, String label, String[] args);

    @Override
    public boolean onCommand(
            @NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return onCommand(commandSender, label, args);
    }
}
