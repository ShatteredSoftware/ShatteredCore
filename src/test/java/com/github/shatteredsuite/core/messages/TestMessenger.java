package com.github.shatteredsuite.core.messages;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.*;

public class TestMessenger {

    private static CommandSender sender;
    private static Messages messages;
    private static Messenger messenger;
    private static Messenger messengerNoPrefix;
    private static HashMap<String, String> msgArgs;

    @BeforeAll
    public static void setUpClass() throws FileNotFoundException {
        msgArgs = new HashMap<>();
        msgArgs.put("target", "world");

        // Plugin mocking
        Logger logger = Logger.getLogger("TestLogger");
        logger.setLevel(Level.OFF); // Disable messages.
        JavaPlugin plugin = Mockito.mock(JavaPlugin.class);
        JavaPlugin pluginNoPrefix = Mockito.mock(JavaPlugin.class);
        // Return messages.yml when we look for a plugin resource.
        Mockito.lenient().when(plugin.getResource(any(String.class)))
                .thenReturn(new FileInputStream(new File(Objects.requireNonNull(
                        TestMessages.class.getClassLoader().getResource("messages.yml")).getPath()))
                );
        Mockito.lenient().when(pluginNoPrefix.getResource(any(String.class)))
                .thenReturn(new FileInputStream(new File(Objects.requireNonNull(
                        TestMessages.class.getClassLoader().getResource("messages_no_prefix.yml")).getPath()))
                );
        Mockito.lenient().when(plugin.getLogger()).thenReturn(logger);
        Mockito.lenient().when(pluginNoPrefix.getLogger()).thenReturn(logger);
        // Load external messages as the config.
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(new File(
                Objects.requireNonNull(TestMessages.class.getClassLoader().getResource("external_messages.yml"))
                        .getPath()));

        // Message mocking
        messages = new Messages(plugin, configuration);
        messenger = new Messenger(plugin, messages);
        Messages messagesNoPrefix = new Messages(pluginNoPrefix, configuration);
        messengerNoPrefix = new Messenger(pluginNoPrefix, messagesNoPrefix);

    }

    @BeforeEach
    public void setUp() {
        sender = Mockito.mock(CommandSender.class);
    }

    @Test
    @DisplayName("should log when a message cannot be found")
    public void testNullMessage() {
        String message = messenger.getMessage("message-that-does-not-exist", null);
        assertThat(message, is(""));
    }

    @Test
    @DisplayName("should be able to get messages")
    public void testGetMessage() {
        String message = messenger.getMessage("prefix", null);
        assertThat(message, is("\u00a76TestPlugin \u00a77\u00bb "));
    }

    @Test
    @DisplayName("should be able to get messages and parse placeholders")
    public void testGetMessagePlaceholders() {
        HashMap<String, String> msgArgs = new HashMap<>();
        msgArgs.put("target", "world");
        String message = messenger.getMessage("hello", msgArgs);
        assertThat(message, is("\u00a7eHello, world!"));
    }
    @Test
    @DisplayName("should throw an exception when sender is null")
    public void testNullSender() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> messenger.sendMessage(null, "hello"));
    }

    @Test
    @DisplayName("should be able to send messages")
    public void testSendMessageRaw() {
        messenger.sendMessage(sender, "hello");
        Mockito.verify(sender).sendMessage("\u00a7eHello, %target%!");
    }


    @Test
    @DisplayName("should not send message if message does not exist")
    public void testSendNullMessage() {
        messenger.sendMessage(sender, "this-message-does-not-exist", msgArgs);
        Mockito.verify(sender, Mockito.never()).sendMessage(anyString());
    }

    @Test
    @DisplayName("should be able to send messages with parameters")
    public void testSendMessage() {
        messenger.sendMessage(sender, "hello", msgArgs);
        Mockito.verify(sender).sendMessage("\u00a7eHello, world!");
    }

    @Test
    @DisplayName("should be able to send messages with a prefix")
    public void testSendMessagePrefix() {
        messenger.sendMessage(sender, "hello", true);
        Mockito.verify(sender).sendMessage("\u00a76TestPlugin \u00a77\u00bb \u00a7eHello, %target%!");
    }

    @Test
    @DisplayName("should be able to send messages with a prefix and arguments")
    public void testSendMessagePrefixArgs() {
        messenger.sendMessage(sender, "hello", msgArgs, true);
        Mockito.verify(sender).sendMessage("\u00a76TestPlugin \u00a77\u00bb \u00a7eHello, world!");
    }

    @Test
    @DisplayName("should be able to send messages with a prefix when prefix does not exist")
    public void testSendMessagePrefixNoPrefix() {
        messengerNoPrefix.sendMessage(sender, "hello", true);
        Mockito.verify(sender).sendMessage("\u00a7eHello, %target%!");
    }

    @Test
    @DisplayName("should be able to send messages with a prefix and arguments when prefix does not exist")
    public void testSendMessagePrefixArgsNoPrefix() {
        messengerNoPrefix.sendMessage(sender, "hello", msgArgs, true);
        Mockito.verify(sender).sendMessage("\u00a7eHello, world!");
    }

    @Test
    @DisplayName("should be able to send error messages")
    public void testSendErrorMessage() {
        messenger.sendErrorMessage(sender, "hello", msgArgs, true);
        messenger.sendErrorMessage(sender, "hello",  true);
        messenger.sendErrorMessage(sender, "hello", msgArgs);
        messenger.sendErrorMessage(sender, "hello");
        Mockito.verify(sender, Mockito.atMost(1)).sendMessage("\u00a76TestPlugin \u00a77\u00bb \u00a7eHello, world!");
        Mockito.verify(sender, Mockito.atMost(1)).sendMessage("\u00a76TestPlugin \u00a77\u00bb \u00a7eHello, %target%!");
        Mockito.verify(sender, Mockito.atMost(1)).sendMessage("\u00a7eHello, world!");
        Mockito.verify(sender, Mockito.atMost(1)).sendMessage("\u00a7eHello, %target%!");
    }

    @Test
    @DisplayName("should be able to send important messages")
    public void testSendImportantMessage() {
        messenger.sendImportantMessage(sender, "hello", msgArgs, true);
        messenger.sendImportantMessage(sender, "hello",  true);
        messenger.sendImportantMessage(sender, "hello", msgArgs);
        messenger.sendImportantMessage(sender, "hello");
        Mockito.verify(sender, Mockito.atMost(1)).sendMessage("\u00a76TestPlugin \u00a77\u00bb \u00a7eHello, world!");
        Mockito.verify(sender, Mockito.atMost(1)).sendMessage("\u00a76TestPlugin \u00a77\u00bb \u00a7eHello, %target%!");
        Mockito.verify(sender, Mockito.atMost(1)).sendMessage("\u00a7eHello, world!");
        Mockito.verify(sender, Mockito.atMost(1)).sendMessage("\u00a7eHello, %target%!");
    }
}
