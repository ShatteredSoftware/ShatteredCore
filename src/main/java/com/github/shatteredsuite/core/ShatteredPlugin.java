package com.github.shatteredsuite.core;

import com.github.shatteredsuite.core.messages.Messageable;
import com.github.shatteredsuite.core.messages.Messages;
import com.github.shatteredsuite.core.messages.Messenger;
import com.github.shatteredsuite.core.updates.UpdateChecker;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Level;

public abstract class ShatteredPlugin extends JavaPlugin implements Messageable {
    private boolean loaded;

    protected Metrics metrics;

    protected Messenger messenger;
    protected int bStatsId = 0;
    protected int spigotResourceId = 0;
    protected boolean updateAvailable;
    private String latestVersion;

    protected void load() throws Exception { }

    protected void postEnable() { }

    protected void preDisable() { }

    protected void onFirstTick() { }

    @Override
    public void onLoad() {
        loaded = true;
        try {
            loadMessages();
            load();
        } catch (Throwable t) {
            getLogger().log(Level.SEVERE, "An error occurred while loading.", t);
            loaded = false;
        }
    }

    @Override
    public void onEnable() {
        if (!loaded) {
            getLogger().severe(
                "Plugin cannot be enabled due to an error that occurred during the plugin loading phase");
            setEnabled(false);
            return;
        }
        if(this.bStatsId != 0) {
            this.metrics = new Metrics(this, this.bStatsId);
        }
        if(this.spigotResourceId != 0) {
            UpdateChecker updateChecker = new UpdateChecker(this, this.spigotResourceId);
            updateChecker.getVersion(version -> {
                if(this.getDescription().getVersion().startsWith(version)) {
                    getLogger().info("You are up to date.");
                    updateAvailable = false;
                }
                else {
                    getLogger().info("Version " + version + " is available.");
                    updateAvailable = true;
                }
                this.latestVersion = version;
            });
        }
        loadMessages();
        Bukkit.getScheduler().runTask(this, this::onFirstTick);
        this.postEnable();
    }

    @Override
    public void onDisable() {
        this.preDisable();
    }

    private void loadMessages() {
        if (!getDataFolder().exists()) {
            //noinspection ResultOfMethodCallIgnored
            getDataFolder().mkdirs();
        }
        File messagesFile = new File(getDataFolder(), "messages.yml");

        if (!messagesFile.exists()) {
            saveResource("messages.yml", false);
        }
        Messages messages = new Messages(this, YamlConfiguration.loadConfiguration(messagesFile));
        messenger = new Messenger(this, messages);
    }

    public Messenger getMessenger() {
        return this.messenger;
    }

    public boolean isUpdateAvailable() {
        return this.updateAvailable;
    }

    public String getLatestVersion() {
        return this.latestVersion;
    }
}
