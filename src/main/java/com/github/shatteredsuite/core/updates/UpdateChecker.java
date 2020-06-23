package com.github.shatteredsuite.core.updates;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Consumer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * An update checker for Spigot plugins.
 */
public class UpdateChecker {

    private final Plugin plugin;
    private final int resourceId;
    private static final String REQUEST_URL = "https://api.spiget.org/v2/resources/";
    private static final String REQUEST_PATH = "/versions";

    /**
     * Creates an update checker.
     *
     * @param plugin The plugin to check for updates for.
     * @param resourceId The resource ID of the plugin.
     */
    public UpdateChecker(Plugin plugin, int resourceId) {
        this.plugin = plugin;
        this.resourceId = resourceId;
    }

    public void getVersion(final Consumer<String> consumer) {
        try {
            URL url = new URL(REQUEST_URL + this.resourceId + REQUEST_PATH);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.addRequestProperty("User-Agent", plugin.getName() + "VersionCheck");

            InputStream inputStream = conn.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);

            JsonElement element = JsonParser.parseReader(reader);
            if(element.isJsonArray()) {
                JsonObject el = element.getAsJsonArray().get(0).getAsJsonObject();
                String name = el.get("name").getAsString();
                consumer.accept(name);
            }
            reader.close();
            inputStream.close();
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
