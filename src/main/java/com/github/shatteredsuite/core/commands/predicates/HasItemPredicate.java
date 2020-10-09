package com.github.shatteredsuite.core.commands.predicates;

import com.github.shatteredsuite.core.commands.responses.PredicateResponse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class HasItemPredicate extends SenderPlayerPredicate {

    private final ItemStack itemStack;

    public HasItemPredicate(PredicateResponse response, ItemStack itemStack) {
        super(response);
        this.itemStack = itemStack;
        this.name = "Player has the item.";
    }

    @Override
    public boolean test(CommandContext context) {
        boolean playerHasItem = false;
        if (super.test(context)) {
            ItemStack[] inventoryContents = ((Player) context.sender).getInventory().getContents();
            playerHasItem = Arrays.asList(inventoryContents).contains(itemStack);
        }
        return playerHasItem;
    }
}
