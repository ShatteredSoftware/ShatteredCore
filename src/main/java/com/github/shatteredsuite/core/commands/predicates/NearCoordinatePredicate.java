package com.github.shatteredsuite.core.commands.predicates;

import com.github.shatteredsuite.core.commands.responses.PredicateResponse;
import com.github.shatteredsuite.core.util.CoordinateUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class NearCoordinatePredicate extends SenderPlayerPredicate {
    private final Location coordinate;
    private final double radius;

    public NearCoordinatePredicate(PredicateResponse response, Location coordinate, double radius) {
        super(response);
        this.coordinate = coordinate;
        this.radius = radius;
        this.name = "Player has the item in their main hand.";
    }

    @Override
    public boolean test(CommandContext context) {
        if (super.test(context)) {
            Player player = (Player) context.sender;
            return CoordinateUtil.distance3D(player.getLocation(), coordinate) <= radius;
        }
        return false;
    }
}
