package com.github.shatteredsuite.core.commands.predicates;

import com.github.shatteredsuite.core.commands.responses.PredicateResponse;

public class ChildPredicate extends CommandContextPredicate {
    public ChildPredicate(PredicateResponse response) {
        super(response);
    }

    @Override
    public boolean test(CommandContext context) {
        return context.args.length > 0 && context.command.inChildren(context.args[0]);
    }
}
