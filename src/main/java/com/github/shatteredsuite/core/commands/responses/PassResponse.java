package com.github.shatteredsuite.core.commands.responses;

import com.github.shatteredsuite.core.commands.predicates.CommandContext;

public abstract class PassResponse implements PredicateResponse {
    @Override
    public CommandContext onSuccess(CommandContext context) {
        return context;
    }
}
