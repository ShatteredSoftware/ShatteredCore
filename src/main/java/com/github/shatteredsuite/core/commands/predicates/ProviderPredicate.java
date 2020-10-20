package com.github.shatteredsuite.core.commands.predicates;

import com.github.shatteredsuite.core.commands.responses.PredicateResponse;
import com.github.shatteredsuite.core.util.Provider;

public class ProviderPredicate extends CommandContextPredicate {
    private final Provider<Boolean> provider;

    public ProviderPredicate(PredicateResponse response, Provider<Boolean> provider) {
        super(response);
        this.provider = provider;
    }

    @Override
    public boolean test(CommandContext context) {
        return provider.get();
    }
}
