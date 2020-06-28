package com.github.shatteredsuite.core.commands;

import com.github.shatteredsuite.core.validation.ArgumentValidationException;
import com.github.shatteredsuite.core.validation.ChoiceValidator;
import com.github.shatteredsuite.core.validation.Validators;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;

public class ArgParser {
    public static void validLength(String[] args, int startingIndex) throws ArgumentValidationException {
        if (args.length <= startingIndex) {
            throw new ArgumentValidationException("Not Enough Arguments.", ArgumentValidationException.ValidationErrorType.NOT_ENOUGH_ARGS, "not-enough-args-nc", String.valueOf(args.length));
        }
    }

    public static Location validLocation(String[] args, int startingIndex) throws ArgumentValidationException {
        validLength(args, startingIndex);
        validLength(args, startingIndex + 5);
        double x = validDouble(args, startingIndex);
        double y = validDouble(args, startingIndex + 1);
        double z = validDouble(args, startingIndex + 2);
        float pitch = validFloat(args, startingIndex + 3);
        float yaw = validFloat(args, startingIndex + 4);
        World world = validWorld(args, startingIndex + 5);
        return new Location(world, x, y, z, yaw, pitch);
    }

    public static Location validShortLocation(String[] args, int startingIndex, Player player) throws ArgumentValidationException {
        validLength(args, startingIndex);
        validLength(args, startingIndex + 3);
        double x = validDouble(args, startingIndex);
        double y = validDouble(args, startingIndex + 1);
        double z = validDouble(args, startingIndex + 2);
        float pitch = Math.round(player.getLocation().getPitch() / 45) * 45;
        float yaw = Math.round(player.getLocation().getYaw() / 45) * 45;
        World world = player.getWorld();
        return new Location(world, x, y, z, pitch, yaw);
    }

    public static int validInt(String[] args, int startingIndex) throws ArgumentValidationException {
        validLength(args, startingIndex);
        return Validators.integerValidator.validate(args[startingIndex]);
    }

    public static float validFloat(String[] args, int startingIndex) throws ArgumentValidationException {
        validLength(args, startingIndex);
        return Validators.floatValidator.validate(args[startingIndex]);
    }

    public static double validDouble(String[] args, int startingIndex) throws ArgumentValidationException {
        validLength(args, startingIndex);
        return Validators.doubleValidator.validate(args[startingIndex]);
    }

    public static World validWorld(String[] args, int startingIndex) throws ArgumentValidationException {
        validLength(args, startingIndex);
        return Validators.worldValidator.validate(args[startingIndex]);
    }

    public static String validChoice(String[] args, int startingIndex, List<String> choices) throws ArgumentValidationException {
        validLength(args, startingIndex);
        return new ChoiceValidator(choices).validate(args[startingIndex]);
    }

    public static Player validPlayer(String[] args, int startingIndex) throws ArgumentValidationException {
        validLength(args, startingIndex);
        return Validators.playerValidator.validate(args[startingIndex]);
    }
}
