<p align="center"><img src="https://raw.githubusercontent.com/ShatteredSuite/ShatteredCore/master/header.png" alt=""/></p>

-----
<p align="center">
<a href="https://github.com/ShatteredSuite/ShatteredCore/blob/master/LICENSE"><img alt="License" src="https://img.shields.io/github/license/ShatteredSuite/ShatteredCore?style=for-the-badge&logo=github" /></a>
<a href="https://github.com/ShatteredSuite/ShatteredCore/issues"><img alt="GitHub Issues" src="https://img.shields.io/github/issues/ShatteredSuite/ShatteredCore?style=for-the-badge&logo=github" /></a>
<a href="https://github.com/ShatteredSuite/ShatteredCore/releases"><img alt="GitHub Version" src="https://img.shields.io/github/release/ShatteredSuite/ShatteredCore?label=Github%20Version&style=for-the-badge&logo=github" /></a>
<a href="https://discord.gg/zUbNX9t"><img alt="Discord" src="https://img.shields.io/badge/Get%20Help-On%20Discord-%237289DA?style=for-the-badge&logo=discord" /></a>
<a href="ko-fi.com/uberpilot"><img alt="Ko-Fi" src="https://img.shields.io/badge/Support-on%20Ko--fi-%23F16061?style=for-the-badge&logo=ko-fi" /></a>
</p>

## For Server Owners

You're probably here because *another* guy wants you to install not one plugin, but two. I know. I promise, this is worth
it. Most of this page is dedicated to good things this does for Developers, allowing for faster bugfixes and features.

### Installation

1. Make sure you're running a reasonably up to date fork of Bukkit. I recommend 
[Tuinity](https://github.com/Spottedleaf/Tuinity) if possible, [Paper](https://papermc.io) otherwise.
2. Grab the latest version of this library from the releases [here](https://github.com/ShatteredSuite/ShatteredCore/releases).
3. Drop it into your `/plugins` folder.
4. Stop your server if it was running. Start your server.
5. You're done!

## For Developers

You're probably here because *another* guy wants you to use his library, or you want to depend on one of my plugins and 
you've found that this is a dependency. Here's what it does. If you're just here to install and don't care, installation
is all the way at the bottom.

ShatteredCore is a shared-code library that helps ShatteredSuite plugins run better and be developed more quickly. It also 
includes a few outside libraries so that they only need to be installed once on the server. Some of the functionality that 
ShatteredCore provides is:

### Library Access
Each of these libraries can be accessed when ShatteredCore is loaded. Some of these are shaded.
* [SmartInvs](https://github.com/MinusKube/SmartInvs) by MinusKube (Shaded)
* [NBTApi](https://github.com/tr7zw/Item-NBT-API) by tr7zw (Shaded)
* [XSeries](https://github.com/CryptoMorin/XSeries) by CryptoMorin (Shaded)
* [Gson](https://github.com/google/gson) by Google (Included)
* [Kotlin Standard Library](https://kotlinlang.org/) by JetBrains (Included)

### Command System
ShatteredCore provides the functionality to write commands in a much more straightforward way, and then offloads most of 
the common error handling onto the library itself. Take for example a normal command to teleport a player:
```java
public class TeleportCommand implements CommandExecutor {
    private final JavaPlugin instance;

    public TeleportCommand(JavaPlugin instance) {
        this.instance = instance;
    }

    public boolean onCommand(CommandSender sender, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("Console is not allowed to use that command.");
            return true;
        }
        if(!sender.hasPermission("teleportplugin.tpme")) {
            sender.sendMessage("You don't have permission to use that.");
            return true;
        }
        if(args.length < 1) {
            sender.sendMessage("Not enough arguments");
            return true;
        }
        Player player = Bukkit.getPlayer(args[0]);
        if(player == null) {
            sender.sendMessage("Could not find that player.");
            return true;
        }
        ((Player) sender).teleport(player);
        sender.sendMessage("Teleported you to " + player.getDisplayName());
        return true;
    }
}
```

This can be replaced with the following:

```java
public class PlayerTeleportCommand extends LeafCommand {
    public PlayerTeleportCommand(Messageable instance) {
        // Set up permission, help message name, and command label.
        super(instance, null, "tpme", "teleportplugin.tpme", "command.tpme");
        // Make sure this is a player.
        this.contextPredicates.put("no-console", new PlayerPredicate());
        // Show command help if there aren't enough arguments.
        this.contextPredicates.put("args", new ArgMinPredicate(new CancelResponse(this.helpPath), 1));
        // Add an alias (useful in child commands!).
        this.addAlias("tpto");
    }

    public void execute(CommandContext ctx) {
        // Get a player, and send them a (configurable!) message we can't find them.
        Player player = ArgParser.validPlayer(ctx.args[0]);
        ((Player) ctx.sender).teleport(player);
        // Add arguments to message system.
        ctx.contextMessages.put("displayname", player.getDisplayName());
        ctx.contextMessages.put("name", player.getName());
        // Send message with message system.
        ctx.messenger.sendMessage(ctx.sender, "teleported", ctx.contextMessages);
    }
}
```
Twice the features in half the space! This system allows developers to focus on building the functionality of commands, 
rather than focusing on handling errors and edge cases. Did I mention that we also have utilities that will write 
TabCompleters as well? This system also supports building complex command paths through a collection of branch commands and 
leaf commands. Check out our Documentation for more information.

### Message System

Never get complaints about hardcoded messages again. A rich messaging system combined with fully customizable messages
means that nearly every message or string in your plugin can be customized easily. This hooks into the command system as 
well, allowing common errors to all use the same message, and only require changes in one place!

Messages are split into three different types -- normal messages, error messages, and important messages. Error messages 
and important messages both include their own sound effects, and all messages support placeholders to be added before 
sending them.

### Manager System

Tired of writing code to keep sets of data managed? Our Identified and Manager classes are here for you! Identified allows
the enforcement of IDs, and Manager will keep track of any Identified items for you. It's super simple!

### Installation

First, add this to your `pom.xml` or `build.gradle`
#### Maven
Add the following to your `pom.xml`:
```xml
<repositories> 
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.ShatteredSuite</groupId>
        <artifactId>ShatteredCore</artifactId>
        <version>Tag</version>
    </dependency>
</dependencies>
```

#### Gradle
Add the following to your `build.gradle`:
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.ShatteredSuite:ShatteredCore:Tag'
}
```

Next, add a dependency in your `plugin.yml`:
```yaml
depend:
- ShatteredCore
```

Finally, use any of the features you like!