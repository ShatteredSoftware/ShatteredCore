package com.github.shatteredsuite.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public final class TabCompleters {
    private TabCompleters() {}

    /**
     * Completes a location in "x y z pitch yaw world" format.
     */
    public static List<String> completeLocationPlayer(String[] args, int startingArg, Player sender) {
        if(args.length <= startingArg) {
            return Collections.emptyList();
        }
        if(args.length == startingArg + 1) {
            return Collections.singletonList(String.valueOf(sender.getLocation().getBlockX()));
        }
        if(args.length == startingArg + 2) {
            return Collections.singletonList(String.valueOf(sender.getLocation().getBlockY()));
        }
        if(args.length == startingArg + 3) {
            return Collections.singletonList(String.valueOf(sender.getLocation().getBlockZ()));
        }
        if(args.length == startingArg + 4) {
            return Collections.singletonList(String.valueOf(sender.getLocation().getPitch()));
        }
        if(args.length == startingArg + 5) {
            return Collections.singletonList(String.valueOf(sender.getLocation().getYaw()));
        }
        if(args.length == startingArg + 6) {
            List<World> worlds = Bukkit.getWorlds();
            List<String> results = worlds.stream().map(World::getName).sorted().collect(Collectors.toList());
            return completeFromOptions(args, startingArg + 5, results);
        }
        return Collections.emptyList();
    }

    public static List<String> completeFromOptions(String[] args, int startingArg, List<String> options) {
        if(args.length < startingArg) {
            return Collections.emptyList();
        }
        else if(args.length == startingArg) {
            Collections.sort(options);
            return options;
        }
        else if(args.length == startingArg + 1) {
            List<String> results = new ArrayList<>();
            StringUtil.copyPartialMatches(args[startingArg], options, results);
            Collections.sort(results);
            return results;
        }
        else {
            return Collections.emptyList();
        }
    }

    public static List<String> completeNumbers(String[] args, int startingArg, ToIntFunction<Integer> modifier, int low, int high) {
        if(args.length < startingArg) {
            return Collections.emptyList();
        }
        else if(args.length == startingArg || args.length == startingArg + 1) {
            List<String> results = new ArrayList<>();
            for(int i = low; i < high; i++) {
                results.add(String.valueOf(modifier.applyAsInt(i)));
            }
            return results;
        }
        return Collections.emptyList();
    }

    public static List<String> completePowersOfTen(String[] args, int startingArg, int amount) {
        return completeNumbers(args, startingArg, (it) -> (int) Math.pow(10, it), 0, amount);
    }

    public static List<String> completeOdds(String[] args, int startingArg, int amount) {
        return completeNumbers(args, startingArg, (it) -> 1 + (2 * it), 0, amount);
    }

    public static List<String> completeEvens(String[] args, int startingArg, int amount) {
        return completeNumbers(args, startingArg, (it) -> 2 + (2 * it), 0, amount);
    }
}
