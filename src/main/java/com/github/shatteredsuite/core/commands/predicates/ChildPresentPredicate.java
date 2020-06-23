package com.github.shatteredsuite.core.commands.predicates;

public class ChildPresentPredicate extends CommandContextPredicate {
    public ChildPresentPredicate(PredicateResponse response) {
        super(response);
    }

    @Override
    public boolean test(CommandContext context) {
        return context.args.length > 0 && context.command.inChildren(context.args[0]);
    }
}
