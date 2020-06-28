package com.github.shatteredsuite.core.commands;

import com.github.shatteredsuite.core.commands.predicates.CommandContext;
import com.github.shatteredsuite.core.commands.predicates.CommandContextPredicate;
import com.github.shatteredsuite.core.messages.Messageable;
import com.github.shatteredsuite.core.validation.ArgumentValidationException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

public abstract class WrappedCommand extends SimpleCommandExecutor
        implements TabCompleter, CommandExecutor {

    protected final HashMap<String, WrappedCommand> children;
    protected final HashMap<String, CommandContextPredicate> contextPredicates = new HashMap<>();
    private final Messageable instance;
    private final WrappedCommand parent;
    private final String label, permission, helpPath;
    private final TreeSet<String> aliases;

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

    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {
        try {
            CommandContext context = contextFromCommand(sender, args);
            if(args.length > 0 && args[0].equalsIgnoreCase("debug") && !children.containsKey("debug")) {
                context = context.debug().consumeArgument();
            }
            run(context);
        }
        catch (ArgumentValidationException ex) {
            HashMap<String, String> errorArgs = new HashMap<>();
            errorArgs.put("label", label);
            errorArgs.put("offender", ex.offender);
            errorArgs.put("options", ex.options);
            instance.getMessenger().sendErrorMessage(sender, ex.errorKey, errorArgs, true);
        }
        return true;
    }

    /**
     * Runs a command, checking predicates. Call this method to run the command.
     *
     * @param ctx A context object containing information about the command's use.
     */
    public void run(@NotNull CommandContext ctx) {
        ctx.contextMessages.put("permission", getPermission());
        for (CommandContextPredicate predicate : this.contextPredicates.values()) {
            ctx = predicate.apply(ctx);
        }
        if (!ctx.cancelled) {
            execute(ctx);
        }
    }

    /**
     * Executes the command without checking predicates. Override this in child classes.
     * <br><br>
     * <b>DO NOT CALL THIS METHOD! CALL {@link #run(CommandContext)} INSTEAD!</b>
     *
     * @param context A context object containing information about the command's use.
     */
    protected void execute(@NotNull CommandContext context) { }

    private CommandContext contextFromCommand(CommandSender sender, String[] args) {
        return new CommandContext(this, sender, label,
                com.github.shatteredsuite.core.util.StringUtil.fixArgs(args), instance.getMessenger(), false);
    }

    public List<String> tabComplete(CommandContext ctx) {
        if(hasPerms(ctx.sender)) {
            return this.onTabComplete(ctx);
        }
        return Collections.emptyList();
    }

    @Override
    public List<String> onTabComplete(
            @NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if(hasPerms(sender)) {
            return tabComplete(contextFromCommand(sender, args));
        }
        return Collections.emptyList();
    }

    public List<String> onTabComplete(@NotNull CommandContext ctx) {
        return Collections.emptyList();
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
