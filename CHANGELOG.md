# 1.3.8 - 2020-06-27

## Additions

## Changes
* fixArgs now properly handles empty args.

## Deprecations
None

## Removals
None

## Security Patches
None

# 1.3.7 - 2020-06-27

## Additions

## Changes
* fixArgs now doesn't remove the last arg if it's empty.

## Deprecations
None

## Removals
None

## Security Patches
None

# 1.3.6 - 2020-06-24

## Additions

## Changes
* fixArgs now returns a list with one element if the fixed args are "empty"

## Deprecations
None

## Removals
None

## Security Patches
None

# 1.3.5 - 2020-06-24

## Additions
* Add a parsePlayer method in ArgParser.

## Changes
* Load Messages in UTF-8.

## Deprecations
None

## Removals
None

## Security Patches
None

# 1.3.4 - 2020-06-24

## Additions
* Message-sending utilities in CommandContext.

## Changes
None

## Deprecations
None

## Removals
None

## Security Patches
None

# 1.3.3 - 2020-06-23

## Additions
None

## Changes
* Allow items to be deleted from Managers.

## Deprecations
None

## Removals
None

## Security Patches
None

# 1.3.2 - 2020-06-23

## Additions
None

## Changes
None

## Deprecations
None

## Removals
None

## Security Patches
None

# 1.3.1 - 2020-06-23

## Additions
* Added JavaDoc in new API portions.

## Changes
None

## Deprecations
None

## Removals
None

## Security Patches
None

# 1.3.0 - 2020-06-22

## Additions
* Added the execute method to WrappedCommands. This should be used in favor of overriding onCommand.
* Added a basic Validator system.
* Added a collection of CommandContextPredicates.
* Added BranchCommand, specifically made for junctions inside of a command.
* Added LeafCommand, which should be used for functional commands.

## Changes
* Slightly changed the functionality of WrappedCommand. Should function nearly identically.

## Deprecations
* sendHelpOrNoPerms in WrappedCommand has been deprecated in favor of the CommandContextPredicate system.

## Removals
None

## Security Patches
None

# 1.2.12 - 2020-06-19

## Additions
None

## Changes
* Make ConfigRecipe more GSON-friendly.

## Deprecations
None

## Removals
None

## Security Patches
None

# 1.2.11 - 2020-06-19

## Additions
None

## Changes
* Fix bug when crafting recipes had spaces.

## Deprecations
None

## Removals
None

## Security Patches
None

# 1.2.10 - 2020-06-19

## Additions
None

## Changes
* Added missing transient modifiers.

## Deprecations
None

## Removals
None

## Security Patches
None

# 1.2.9 - 2020-06-19

## Additions
None

## Changes
* Changed improper visibility in ConfigRecipe.

## Deprecations
None

## Removals
None

## Security Patches
None

# 1.2.8 - 2020-06-19

## Additions
None

## Changes
* Expanded the ConfigRecipe code API.

## Deprecations
None

## Removals
None

## Security Patches
None

# 1.2.7 - 2020-06-03

## Additions
* Include GSON in dist/api jar.

## Changes
None

## Deprecations
None

## Removals
None

## Security Patches
None

# 1.2.5 - 2020-06-02

## Additions
None

## Changes
* Allow configuration of whether messages are required and generated.

## Deprecations
None

## Removals
None

## Security Patches
None

# 1.2.4 - 2020-06-02

## Additions
None

## Changes
None

# 1.2.5 - 2020-06-02

## Additions
None

## Changes
* Make methods on Manager open.

## Deprecations
None

## Removals
None

## Security Patches
None

# 1.2.4 - 2020-06-02

## Additions
None

## Changes
* Package for Manager and Identified are now in `com.github.shatteredsuite.core.util` instead of
`com.github.shatteredsuite.scrolls.data`.

## Deprecations
None

## Removals
None

## Security Patches
None

# 1.2.3 - 2020-06-02

## Additions
* Manager class -- Uses identified objects to build a registry.
* Identified class -- represents some object that has an identifier.

## Changes
None

## Deprecations
None

## Removals
None

## Security Patches
None


# 1.2.2 - 2020-06-02

## Additions
None

## Changes
None

## Deprecations
None

## Removals
* Gradle `install` task removed in favor of `publishToMavenLocal`.

## Security Patches
None


# 1.2.1 - 2020-06-02

## Additions
* Add install task to gradle.

## Changes
None

## Deprecations
None

## Removals
None

## Security Patches
None

# 1.2.0 - 2020-06-02

## Additions
### ShatteredPlugin
* Added the `preload` hook, allowing for registering ConfigurationSerializables before loading configs.
* Reworked the `load` hook setting the `loaded` flag.
* Added the `loadConfig` and `parseConfig` methods. ParseConfig gets the contents of the `loadConfig` function if `this.internalConfig` is set to true.

### CooldownManager
* Added the `reset` method to the CooldownManager.

### Tests
* Added tests for StringUtil and CooldownManager (rudimentary).

## Changes
### ConfigRecipe
* Allow ConfigRecipe to be loaded as `ConfigRecipe` instead of `com.github.shatteredsuite.core.config.ConfigRecipe`

## Deprecations
None

## Removals
None

## Fixes
*  Registered ConfigRecipe as ConfigurationSerializable.

## Security Patches
None

# 1.1.0

### Additions

* Added the foundations for a simplified custom events system.

### Changes
* Modified the build process to produce cleaner and more usable artifacts.

#### Kotlin Extensions
* Moved NBTContainerExt to NBTCompoundExt.

### Removals

None