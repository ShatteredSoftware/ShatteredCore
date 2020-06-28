package com.github.shatteredsuite.core.commands.predicates;

import com.github.shatteredsuite.core.commands.WrappedCommand;
import com.github.shatteredsuite.core.messages.Messenger;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashMap;

public class CommandContext {
    @NotNull
    public final HashMap<String, String> contextMessages = new HashMap<>();
    @NotNull
    public final CommandSender sender;
    @NotNull
    public final String label;
    @NotNull
    public final String[] args;
    @NotNull
    public final WrappedCommand command;
    @NotNull
    public final Messenger messenger;
    public final boolean cancelled;
    @Nullable
    public final String parameter;

    public CommandContext(@NotNull WrappedCommand command, @NotNull CommandSender sender, @NotNull String label, @NotNull String[] args, @NotNull Messenger messenger, boolean cancelled) {
        this(command, sender, label, args, messenger, cancelled, null);
    }

    public CommandContext(@NotNull WrappedCommand command, @NotNull CommandSender sender, @NotNull String label, @NotNull String[] args, @NotNull Messenger messenger, boolean cancelled, String parameter) {
        this.command = command;
        this.sender = sender;
        this.label = label;
        this.args = args;
        this.messenger = messenger;
        this.cancelled = cancelled;
        this.contextMessages.put("label", label);
        this.parameter = parameter;
    }

    public CommandContext cancelled() {
        return new CommandContext(command, sender, label, args, messenger, true);
    }

    public CommandContext consumeArgument() {
        return new CommandContext(command, sender, label, Arrays.copyOfRange(args, 1, args.length), messenger, cancelled);
    }

    public CommandContext nextLevel(WrappedCommand command, String oldLabel) {
        return new CommandContext(command, sender, oldLabel + " " + command.getLabel(), Arrays.copyOfRange(args, 1, args.length), messenger, cancelled);
    }

    public void sendMessage(String id, boolean prefix) {
        this.messenger.sendMessage(sender, id, this.contextMessages, prefix);
    }

    public void sendErrorMessage(String id, boolean prefix) {
        this.messenger.sendErrorMessage(sender, id, this.contextMessages, prefix);
    }

    public void sendImportantMessage(String id, boolean prefix) {
        this.messenger.sendImportantMessage(sender, id, this.contextMessages, prefix);
    }
}
