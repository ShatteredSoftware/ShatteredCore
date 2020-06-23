package com.github.shatteredsuite.core.commands.predicates;

public abstract class PassResponse implements PredicateResponse {
    @Override
    public CommandContext onSuccess(CommandContext context) {
        return context;
    }
}
