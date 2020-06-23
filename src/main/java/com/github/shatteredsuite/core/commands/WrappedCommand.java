package com.github.shatteredsuite.core.commands;

import com.github.shatteredsuite.core.commands.predicates.*;
import com.github.shatteredsuite.core.messages.Messageable;
import com.github.shatteredsuite.core.validation.ArgumentValidationException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public abstract class WrappedCommand extends SimpleCommandExecutor
    implements TabCompleter, CommandExecutor {

    private final Messageable instance;
    private final WrappedCommand parent;
    private final String label, permission, helpPath;
    private final TreeSet<String> aliases;
    protected final HashMap<String, WrappedCommand> children;
    protected final HashMap<String, CommandContextPredicate> contextPredicates = new HashMap<>();

    public WrappedCommand(
        Messageable instance,
        WrappedCommand parent,
        String label,
        String permission,
        String helpPath) {
        this.parent = parent;
        this.instance = instance;
        this.label = label;
        this.permission = permission;
        this.helpPath = helpPath;
        this.aliases = new TreeSet<>();
        children = new HashMap<>();
    }

    public WrappedCommand registerSubcommand(WrappedCommand subcommand) {
        children.putIfAbsent(subcommand.getLabel(), subcommand);
        for (String alias : subcommand.getAliases()) {
            children.putIfAbsent(alias, subcommand);
        }
        return this;
    }

    public WrappedCommand attachToParent() {
        if (this.parent != null) {
            this.parent.registerSubcommand(this);
        }
        return this;
    }

    public WrappedCommand addAlias(String alias) {
        this.aliases.add(alias);
        return this;
    }

    public boolean inChildren(String child) {
        return children.containsKey(child);
    }

    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {
        try {
            CommandContext context = new CommandContext(this, sender, label,
                    com.github.shatteredsuite.core.util.StringUtil.fixArgs(args), instance.getMessenger(), false);
            run(context);
        } catch (ArgumentValidationException ex) {
            HashMap<String, String> errorArgs = new HashMap<>();
            errorArgs.put("label", label);
            errorArgs.put("offender", ex.offender);
            errorArgs.put("options", ex.options);
            instance.getMessenger().sendErrorMessage(sender, ex.errorKey, errorArgs, true);
        }
        return true;
    }

    public void run(@NotNull CommandContext context) {
        context.contextMessages.put("permission", getPermission());
        for(CommandContextPredicate predicate : this.contextPredicates.values()) {
            context = predicate.apply(context);
        }
        if(!context.cancelled) {
            execute(context);
        }
    }

    protected void execute(@NotNull CommandContext context) {}

    @Override
    public List<String> onTabComplete(
            CommandSender sender, Command command, String alias, String[] args) {
        // create new array
        final List<String> completions = new ArrayList<>();
        if (args.length <= 1) {
            StringUtil.copyPartialMatches(args[0], children.keySet(), completions);
            Collections.sort(completions);
            return completions;
        }
        return children.containsKey(args[0])
            ? children
            .get(args[0])
            .onTabComplete(
                sender, command, alias + " " + args[0], Arrays.copyOfRange(args, 1, args.length))
            : null;
    }

    /**
     * @deprecated Use the predicate system. Will be removed in 1.4.
     */
    protected boolean showHelpOrNoPerms(CommandSender sender, String label, String[] args) {
        HashMap<String, String> msgArgs = new HashMap<>();
        msgArgs.put("label", label);
        if (!hasPerms(sender)) {
            instance.getMessenger().sendMessage(sender, "no-permission", true);
            return false;
        }
        if (args.length == 0 || (!children.isEmpty() && !children.containsKey(args[0]))) {
            instance.getMessenger().sendMessage(sender, helpPath, msgArgs);
            return false;
        }
        return true;
    }

    public boolean hasPerms(CommandSender sender) {
        return sender.hasPermission(permission);
    }

    public String getLabel() {
        return label;
    }

    public TreeSet<String> getAliases() {
        return aliases;
    }

    public CommandExecutor getParent() {
        return parent;
    }

    public String getPermission() {
        return permission;
    }

    public String getHelpPath() {
        return helpPath;
    }
}
