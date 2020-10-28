package com.github.shatteredsuite.core.commands.predicates;

import com.github.shatteredsuite.core.commands.responses.PredicateResponse;
import com.github.shatteredsuite.core.util.CoordinateUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class NearColumnPredicate extends SenderPlayerPredicate {
    private final Location column;
    private final double radius;

    public NearColumnPredicate(PredicateResponse response, Location column, double radius) {
        super(response);
        this.column = column;
        this.radius = radius;
        this.name = "Player has the item in their main hand.";
    }

    @Override
    public boolean test(CommandContext context) {
        if (super.test(context)) {
            Player player = (Player) context.sender;
            return CoordinateUtil.distance2D(player.getLocation(), column) <= radius;
        }
        return false;
    }
}
