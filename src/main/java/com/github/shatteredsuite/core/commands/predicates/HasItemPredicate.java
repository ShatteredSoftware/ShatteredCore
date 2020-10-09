package com.github.shatteredsuite.core.commands.predicates;

import com.github.shatteredsuite.core.commands.responses.PredicateResponse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HasItemPredicate extends SenderPlayerPredicate {

    private final int index;

    public HasItemPredicate(PredicateResponse response, int index) {
        super(response);
        this.index = index;
        this.name = "Player has an item.";
    }

    @Override
    public boolean test(CommandContext context) {
        boolean playerHasItem = false;
        if (super.test(context)) {
            ItemStack itemStack = ((Player) context.sender).getInventory().getItem(index);
            playerHasItem = itemStack != null && itemStack.getAmount() > 0;
        }
        return playerHasItem;
    }
}
