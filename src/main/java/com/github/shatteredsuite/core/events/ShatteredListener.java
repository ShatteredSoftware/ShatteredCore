package com.github.shatteredsuite.core.events;

import com.github.shatteredsuite.core.ShatteredPlugin;
import org.bukkit.event.Listener;

public class ShatteredListener implements Listener {
    protected final ShatteredPlugin plugin;

    public ShatteredListener(ShatteredPlugin plugin) {
        this.plugin = plugin;
    }

    public void register() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
}
