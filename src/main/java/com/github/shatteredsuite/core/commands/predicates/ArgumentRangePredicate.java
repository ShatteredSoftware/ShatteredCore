package com.github.shatteredsuite.core.commands.predicates;

import com.github.shatteredsuite.core.commands.responses.PredicateResponse;

public class ArgumentRangePredicate extends CommandContextPredicate {
    private final int low;
    private final int high;

    public ArgumentRangePredicate(PredicateResponse response, int low, int high) {
        super(response);
        this.low = low;
        this.high = high;
        this.name = "Contains between " + low + " and " + high + " arguments.";
    }

    public ArgumentRangePredicate(PredicateResponse response, int target) {
        super(response);
        this.low = target;
        this.high = target;
    }

    @Override
    public boolean test(CommandContext context) {
        return context.args.length <= high && context.args.length >= low;
    }
}
