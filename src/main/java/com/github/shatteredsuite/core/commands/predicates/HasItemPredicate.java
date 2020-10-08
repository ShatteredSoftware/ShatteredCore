package com.github.shatteredsuite.core.commands.predicates;

import com.github.shatteredsuite.core.commands.responses.PredicateResponse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static java.util.Arrays.stream;

public class HasItemPredicate extends SenderPlayerPredicate {

    public HasItemPredicate(PredicateResponse response) {
        super(response);
        this.name = "Player has an item.";
    }

    @Override
    public boolean test(CommandContext context) {
        boolean playerHasItem = false;
        if (super.test(context)) {
            ItemStack[] playerInventoryContents = ((Player) context.sender).getInventory().getContents();
            playerHasItem = stream(playerInventoryContents).anyMatch(itemStack -> itemStack.getAmount() > 0);
        }
        return playerHasItem;
    }
}
