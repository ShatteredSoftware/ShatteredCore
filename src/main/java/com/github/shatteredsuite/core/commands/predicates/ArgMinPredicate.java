package com.github.shatteredsuite.core.commands.predicates;

public class ArgMinPredicate extends CommandContextPredicate {
    private final int min;
    private final String expected;

    public ArgMinPredicate(PredicateResponse response, int min) {
        super(response);
        this.min = min;
        expected = null;
    }

    public ArgMinPredicate(PredicateResponse response, int min, String expected) {
        super(response);
        this.min = min;
        this.expected = expected;
    }

    @Override
    public boolean test(CommandContext context) {
        context.contextMessages.put("argx", expected != null ? expected : String.valueOf(min));
        context.contextMessages.put("argc", String.valueOf(context.args.length));
        return context.args.length >= min;
    }
}
