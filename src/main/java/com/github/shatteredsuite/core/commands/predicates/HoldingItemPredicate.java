package com.github.shatteredsuite.core.commands.predicates;

import com.cryptomorin.xseries.XMaterial;
import com.github.shatteredsuite.core.commands.responses.PredicateResponse;
import org.bukkit.entity.Player;

public class HoldingItemPredicate extends SenderPlayerPredicate {
    public HoldingItemPredicate(PredicateResponse response) {
        super(response);
        this.name = "Player has an item in their main hand.";
    }

    @Override
    public boolean test(CommandContext context) {
        if (super.test(context)) {
            return ((Player) context.sender).getInventory().getItemInMainHand().getType() != XMaterial.AIR.parseMaterial();
        }
        return false;
    }
}
