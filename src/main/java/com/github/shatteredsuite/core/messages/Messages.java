package com.github.shatteredsuite.core.messages;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Objects;

/**
 * A class to store messages and load them from files, internal and external.
 *
 * @author UberPilot
 * @since 1.0.0
 */
public class Messages {

    private final HashMap<String, String> messages;

    /**
     * Creates a set of messages from a file, <code>defaultMessages.yml</code> included in a jar.
     *
     * @param instance The instance of JavaPlugin.
     * @param config   The config to load messages from.
     */
    public Messages(JavaPlugin instance, YamlConfiguration config) {
        messages = new HashMap<>();
        YamlConfiguration defaults =
            YamlConfiguration.loadConfiguration(
                new InputStreamReader(Objects.requireNonNull(instance.getResource("messages.yml")), StandardCharsets.UTF_8));
        for (String key : config.getKeys(true)) {
            //noinspection ConstantConditions
            messages.put(key, ChatColor.translateAlternateColorCodes('&', config.getString(key)));
        }
        for (String key : defaults.getKeys(true)) {
            //noinspection ConstantConditions
            messages.putIfAbsent(
                key, ChatColor.translateAlternateColorCodes('&', defaults.getString(key)));
        }
    }

    /**
     * Gets the message with the given key.
     *
     * @param key The key of the message to get.
     * @return The message, if it exists, or <code>null</code> otherwise.
     */
    public String getMessage(String key) {
        return messages.get(key);
    }
}
