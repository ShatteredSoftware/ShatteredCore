package com.github.shatteredsuite.core.commands.predicates;

import com.github.shatteredsuite.core.commands.responses.PredicateResponse;

import java.util.function.Supplier;

public class SupplierPredicate extends CommandContextPredicate {
    private final Supplier<Boolean> provider;

    public SupplierPredicate(PredicateResponse response, Supplier<Boolean> provider) {
        super(response);
        this.provider = provider;
    }

    @Override
    public boolean test(CommandContext context) {
        return provider.get();
    }
}
