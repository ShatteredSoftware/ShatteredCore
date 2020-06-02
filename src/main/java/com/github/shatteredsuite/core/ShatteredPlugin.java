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
    protected boolean internalConfig;
    protected boolean createMessages;
    private String latestVersion;

    /**
     * Do any work that must be done before loading the config.
     */
    protected void preload() { }

    /**
     * Do any work to be done after loading configs. Register external connections here.
     * @throws Exception Any error that occurs.
     */
    protected void load() throws Exception { }

    protected void postEnable() { }

    protected void preDisable() { }

    protected void onFirstTick() { }

    protected void parseConfig(YamlConfiguration config) { }

    public void loadConfig() {
        if (!getDataFolder().exists()) {
            //noinspection ResultOfMethodCallIgnored
            getDataFolder().mkdirs();
        }

        if(internalConfig) {
            File configFile = new File(getDataFolder(), "config.yml");
            if (!configFile.exists()) {
                saveResource("config.yml", false);
            }
            parseConfig(YamlConfiguration.loadConfiguration(configFile));
        }
    }

    @Override
    public void onLoad() {
        loaded = false;
        try {
            preload();
            loadMessages();
            loadConfig();
            load();
            loaded = true;
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
        if (!createMessages) {
            return;
        }
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
