package com.github.shatteredsuite.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public abstract class SimpleCommandExecutor implements CommandExecutor {

    public abstract boolean onCommand(CommandSender sender, String label, String[] args);

    @Override
    public boolean onCommand(
            CommandSender commandSender, Command command, String label, String[] args) {
        return onCommand(commandSender, label, args);
    }
}
