package com.github.shatteredsuite.core.commands.predicates;

import com.github.shatteredsuite.core.commands.responses.PredicateResponse;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class HoldingSpecificItemPredicate extends SenderPlayerPredicate {

    private final ItemStack itemStack;

    public HoldingSpecificItemPredicate(PredicateResponse response, ItemStack itemStack) {
        super(response);
        this.itemStack = itemStack;
        this.name = "Player has the item in their main hand.";
    }

    @Override
    public boolean test(CommandContext context) {
        if (super.test(context)) {
            return ((Player) context.sender).getInventory().getItemInMainHand().equals(itemStack);
        }
        return false;
    }
}
