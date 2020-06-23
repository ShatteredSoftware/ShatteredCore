package com.github.shatteredsuite.core.commands.predicates;

import org.bukkit.entity.Player;

public class PlayerPredicate extends CommandContextPredicate {
    public PlayerPredicate(PredicateResponse response) {
        super(response);
    }

    public PlayerPredicate() {
        super(new CancelResponse("no-console"));
    }

    @Override
    public boolean test(CommandContext context) {
        return context.sender instanceof Player;
    }
}
