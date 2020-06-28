package com.github.shatteredsuite.core.commands.predicates;

import com.github.shatteredsuite.core.commands.responses.PredicateResponse;

public class ArgumentMinimumPredicate extends CommandContextPredicate {
    private final int min;
    private final String expected;

    public ArgumentMinimumPredicate(PredicateResponse response, int min) {
        this(response, min, null);
    }

    public ArgumentMinimumPredicate(PredicateResponse response, int min, String expected) {
        super(response);
        this.min = min;
        this.expected = expected;
        this.name = "Has at least " + min + " arguments.";
    }

    @Override
    public boolean test(CommandContext context) {
        context.contextMessages.put("argx", expected != null ? expected : String.valueOf(min));
        context.contextMessages.put("argc", String.valueOf(context.args.length));
        return context.args.length >= min;
    }
}
