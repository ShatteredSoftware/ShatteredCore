package com.github.shatteredsuite.core.plugin;

import com.github.shatteredsuite.core.ShatteredPlugin;
import com.github.shatteredsuite.core.config.ConfigRecipe;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

public class ShatteredCore extends ShatteredPlugin {
    protected int bStatsId = 7496;

    public ShatteredCore() {
        this.createMessages = true;
    }


    @Override
    protected void load() {
        ConfigurationSerialization.registerClass(ConfigRecipe.class);
    }

    @Override
    protected void postEnable() {
        getCommand("shatteredcore").setExecutor(new AboutCommand(this));
    }
}
