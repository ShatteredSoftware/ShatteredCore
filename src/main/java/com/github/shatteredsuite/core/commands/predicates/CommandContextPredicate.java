package com.github.shatteredsuite.core.commands.predicates;

import com.github.shatteredsuite.core.commands.responses.PredicateResponse;
import org.bukkit.ChatColor;

import java.util.function.Predicate;

public abstract class CommandContextPredicate implements Predicate<CommandContext> {

    private final PredicateResponse response;
    protected String name;

    public CommandContextPredicate(PredicateResponse response) {
        this.response = response;
    }

    public abstract boolean test(CommandContext context);

    public CommandContext apply(CommandContext context) {
        if (context.cancelled) { // Short circuit.
            return context;
        }
        if (test(context)) {
            if(context.debug) {
                context.sender.sendMessage(ChatColor.GREEN.toString() + "Passed Predicate: " + ChatColor.WHITE.toString() + name);
            }
            return response.onSuccess(context);
        }
        else {
            if(context.debug) {
                context.sender.sendMessage(ChatColor.RED.toString() + "Failed Predicate: " + ChatColor.WHITE.toString() + name);
            }
            return response.onFailure(context);
        }
    }
}
