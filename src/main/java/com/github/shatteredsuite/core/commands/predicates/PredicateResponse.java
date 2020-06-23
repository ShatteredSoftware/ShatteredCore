package com.github.shatteredsuite.core.commands.predicates;

public interface PredicateResponse {
    CommandContext onSuccess(CommandContext context);
    CommandContext onFailure(CommandContext context);
}
