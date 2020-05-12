package com.github.shatteredsuite.core.plugin;

import com.github.shatteredsuite.core.ShatteredPlugin;

public class ShatteredCore extends ShatteredPlugin {
    protected int bStatsId = 7496;

    @Override
    protected void postEnable() {
        getCommand("shatteredcore").setExecutor(new AboutCommand(this));
    }
}
