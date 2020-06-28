package com.github.shatteredsuite.core.commands.predicates;

import com.github.shatteredsuite.core.commands.responses.CancelResponse;
import com.github.shatteredsuite.core.commands.responses.PredicateResponse;
import org.bukkit.entity.Player;

public class SenderPlayerPredicate extends CommandContextPredicate {
    public SenderPlayerPredicate(PredicateResponse response) {
        super(response);
    }

    public SenderPlayerPredicate() {
        super(new CancelResponse("no-console"));
    }

    @Override
    public boolean test(CommandContext context) {
        return context.sender instanceof Player;
    }
}
