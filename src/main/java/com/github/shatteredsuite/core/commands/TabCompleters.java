package com.github.shatteredsuite.core.commands;

import com.github.shatteredsuite.core.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public final class TabCompleters {
    private TabCompleters() {
    }

    /**
     * Completes a location in "x y z pitch yaw world" format.
     *
     * @param args        Space-separated arguments.
     * @param startingArg The argument to start providing completions on.
     * @param sender      The player trying to tab complete the location.
     * @return A list of options, based on the player's current location.
     */
    public static List<String> completeLocationPlayer(String[] args, int startingArg, Player sender) {
        if (args.length <= startingArg + 1) {
            return Collections.singletonList(String.valueOf(sender.getLocation().getBlockX()));
        }
        if (args.length == startingArg + 2) {
            return Collections.singletonList(String.valueOf(sender.getLocation().getBlockY()));
        }
        if (args.length == startingArg + 3) {
            return Collections.singletonList(String.valueOf(sender.getLocation().getBlockZ()));
        }
        if (args.length == startingArg + 4) {
            return Collections.singletonList(String.valueOf(sender.getLocation().getPitch()));
        }
        if (args.length == startingArg + 5) {
            return Collections.singletonList(String.valueOf(sender.getLocation().getYaw()));
        }
        if (args.length == startingArg + 6) {
            List<World> worlds = Bukkit.getWorlds();
            List<String> results = worlds.stream().map(World::getName).sorted().collect(Collectors.toList());
            return completeFromOptions(args, startingArg + 5, results);
        }
        return Collections.emptyList();
    }

    public static List<String> completeBoolean(String[] args, int startingArg) {
        if(args.length <= startingArg) {
            return Arrays.asList("true", "false");
        }
        else {
            List<String> res = new ArrayList<>();
            StringUtil.copyPartialMatches(args[startingArg], Arrays.asList("yes", "no", "true", "false", "enabled", "disabled"), res);
            return res;
        }
    }

    /**
     * Given a list of options, returns the most likely match given the current argument.
     *
     * @param args        The current arguments.
     * @param startingArg The argument to provide completions for.
     * @param options     The list of options.
     * @return A list options ordered by likelihood from the current argument.
     */
    public static List<String> completeFromOptions(String[] args, int startingArg, List<String> options) {
        if (args.length < startingArg) {
            return Collections.emptyList();
        }
        else if (args.length == startingArg) {
            Collections.sort(options);
            return options;
        }
        else if (args.length == startingArg + 1) {
            List<String> results = new ArrayList<>();
            StringUtil.copyPartialMatches(args[startingArg], options, results);
            Collections.sort(results);
            return results;
        }
        else {
            return Collections.emptyList();
        }
    }

    /**
     * Creates a dynamic list of number completions and returns the most likely match.
     *
     * @param args        The current list of arguments.
     * @param startingArg The argument to provide completions for.
     * @param modifier    The function that determines which numbers are returned.
     * @param low         The number to start completions on.
     * @param high        The number to end completions on.
     * @return A list of numbers ordered by likelihood from the current argument.
     * @see #completeEvens(String[], int, int)
     * @see #completeOdds(String[], int, int)
     * @see #completePowersOfTen(String[], int, int)
     */
    public static List<String> completeNumbers(String[] args, int startingArg, ToIntFunction<Integer> modifier, int low, int high) {
        if (args.length < startingArg) {
            return Collections.emptyList();
        }
        else if (args.length == startingArg + 1) {
            List<String> options = new ArrayList<>();
            for (int i = low; i < high; i++) {
                options.add(String.valueOf(modifier.applyAsInt(i)));
            }
            List<String> results = new ArrayList<>();
            StringUtil.copyPartialMatches(args[startingArg], options, results);
            Collections.sort(results);
            return results;
        }
        return Collections.emptyList();
    }

    /**
     * Returns a list of ascending powers of ten.
     *
     * @param args        The current list of arguments.
     * @param startingArg The argument to start providing completions on.
     * @param amount      The number of powers to go through.
     * @return A list of powers of ten ordered by likelihood from the current argument.
     */
    public static List<String> completePowersOfTen(String[] args, int startingArg, int amount) {
        return completeNumbers(args, startingArg, (it) -> (int) Math.pow(10, it), 0, amount);
    }

    /**
     * Returns a list of ascending odds.
     *
     * @param args        The current list of arguments.
     * @param startingArg The argument to start providing completions on.
     * @param amount      The number of odds to go through.
     * @return A list of odds ordered by likelihood from the current argument.
     */
    public static List<String> completeOdds(String[] args, int startingArg, int amount) {
        return completeNumbers(args, startingArg, (it) -> 1 + (2 * it), 0, amount);
    }

    /**
     * Returns a list of ascending evens.
     *
     * @param args        The current list of arguments.
     * @param startingArg The argument to start providing completions on.
     * @param amount      The number of evens to go through.
     * @return A list of evens ordered by likelihood from the current argument.
     */
    public static List<String> completeEvens(String[] args, int startingArg, int amount) {
        return completeNumbers(args, startingArg, (it) -> 2 + (2 * it), 0, amount);
    }
}
