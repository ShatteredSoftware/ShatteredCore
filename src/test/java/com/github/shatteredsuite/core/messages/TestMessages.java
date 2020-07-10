package com.github.shatteredsuite.core.messages;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;

public class TestMessages {
    JavaPlugin plugin;
    YamlConfiguration configuration;
    Messages messages;

    @BeforeEach
    public void setUp() throws FileNotFoundException {
        plugin = Mockito.mock(JavaPlugin.class);
        // Return messages.yml when we look for a plugin resource.
        Mockito.lenient().when(plugin.getResource(any(String.class)))
                .thenReturn(new FileInputStream(new File(Objects.requireNonNull(
                        TestMessages.class.getClassLoader().getResource("messages.yml")).getPath()))
                );
        // Load external messages as the config.
        configuration = YamlConfiguration.loadConfiguration(new File(
                Objects.requireNonNull(TestMessages.class.getClassLoader().getResource("external_messages.yml"))
                        .getPath()));
        messages = new Messages(plugin, configuration);
    }

    @Test
    @DisplayName("should load with proper encoding and replaced colors")
    public void testMessages() {
        assertThat(messages.getMessage("prefix"), is("\u00a76TestPlugin \u00a77\u00bb"));
    }

    @Test
    @DisplayName("should load external messages after internal messages")
    public void testCascadingMessages() {
        assertThat(messages.getMessage("override"), is("\u00a7fOverridden"));
    }
}
