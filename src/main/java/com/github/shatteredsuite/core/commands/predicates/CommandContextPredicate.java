package com.github.shatteredsuite.core.commands.predicates;

import com.github.shatteredsuite.core.commands.responses.PredicateResponse;

import java.util.function.Predicate;

public abstract class CommandContextPredicate implements Predicate<CommandContext> {

    public CommandContextPredicate(PredicateResponse response) {
        this.response = response;
    }

    private final PredicateResponse response;
    public abstract boolean test(CommandContext context);

    public CommandContext apply(CommandContext context) {
        if(context.cancelled) { // Short circuit.
            return context;
        }
        if(test(context)) {
            return response.onSuccess(context);
        }
        else return response.onFailure(context);
    }
}
