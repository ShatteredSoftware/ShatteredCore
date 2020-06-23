package com.github.shatteredsuite.core.validation;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Validators {
    public static final Validator<Integer> integerValidator = (str) -> {
        try {
            return Integer.parseInt(str);
        } catch(NumberFormatException ex) {
            throw new ArgumentValidationException("Could not convert " + str + " to an integer.", ArgumentValidationException.ValidationErrorType.INVALID_FORMAT, "invalid-integer", str);
        }
    };

    public static final Validator<Float> floatValidator = (str) -> {
        try {
            return Float.parseFloat(str);
        } catch(NumberFormatException ex) {
            throw new ArgumentValidationException("Could not convert " + str + " to a float.", ArgumentValidationException.ValidationErrorType.INVALID_FORMAT, "invalid-float", str);
        }
    };

    public static final Validator<Double> doubleValidator = (str) -> {
        try {
            return Double.parseDouble(str);
        } catch(NumberFormatException ex) {
            throw new ArgumentValidationException("Could not convert " + str + " to a double.", ArgumentValidationException.ValidationErrorType.INVALID_FORMAT, "invalid-double", str);
        }
    };

    public static final Validator<World> worldValidator = (str) -> {
        World world = Bukkit.getWorld(str);
        if(world == null) {
            throw new ArgumentValidationException("Could not find a world with name " + str + ".", ArgumentValidationException.ValidationErrorType.INVALID_FORMAT, "invalid-world", str);
        }
        return world;
    };

    public static final Validator<Player> playerValidator = (str) -> {
        Player player = Bukkit.getPlayer(str);
        if(player == null) {
            throw new ArgumentValidationException("Could not find a player with name " + str + ".", ArgumentValidationException.ValidationErrorType.INVALID_FORMAT, "invalid-player", str);
        }
        return player;
    };
}
