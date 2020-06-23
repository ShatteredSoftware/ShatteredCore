package com.github.shatteredsuite.core.commands.predicates;

public class ArgLengthPredicate extends CommandContextPredicate {
    private final int low;
    private final int high;

    public ArgLengthPredicate(PredicateResponse response, int low, int high) {
        super(response);
        this.low = low;
        this.high = high;
    }

    public ArgLengthPredicate(PredicateResponse response, int target) {
        super(response);
        this.low = target;
        this.high = target;
    }

    @Override
    public boolean test(CommandContext context) {
        return context.args.length <= high && context.args.length >= low;
    }
}
