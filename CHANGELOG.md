# 1.4.11 - 2020-08-25

## Additions
* Add support for 1.16.2.

## Changes
* Fix a ConfigRecipe and validator bugs.

## Deprecations
None

## Removals
None

## Security Patches
None

# 1.4.10 - 2020-08-01

## Additions
* Add support for 1.16.1.

## Changes
None

## Deprecations
None

## Removals
None

## Security Patches
None

# 1.4.9 - 2020-06-28

## Additions
* Add default implementation on ExternalProvider.

## Changes
None

## Deprecations
None

## Removals
None

## Security Patches
None

# 1.4.8 - 2020-06-28

## Additions
* Added OutsourcedManager.
* Added getIds to Manager.

## Changes
* Fix bounds errors on TabCompleters.

## Deprecations
None

## Removals
None

## Security Patches
None

# 1.4.7 - 2020-06-28

## Additions
* Added a target argument to the ParameterizedBranchCommand.

## Changes
* Allow the debug option to cascade into further commands.

## Deprecations
None

## Removals
None

## Security Patches
None

# 1.4.6 - 2020-06-28

## Additions
None

## Changes
* Fixed predicates displaying on every command.

## Deprecations
None

## Removals
None

## Security Patches
None

# 1.4.5 - 2020-06-28

## Additions
* Permission checking to tab completing.

## Changes
* Fixed ParameterizedBranchCommand not checking predicates.

## Deprecations
None

## Removals
None

## Security Patches
None

# 1.4.4 - 2020-06-28

## Additions
* Added a boolean validator and completer.

## Changes
* Changed the number completer to complete based on input.

## Deprecations
None

## Removals
None

## Security Patches
None

# 1.4.3 - 2020-06-28

## Additions
* Added a "secret" debug command that shows how the command is flowing through the system.

## Changes
None

## Deprecations
None

## Removals
None

## Security Patches
None

# 1.4.2 - 2020-06-27

## Additions
None

## Changes
* Made ArrayUtil return Lists for `withoutIndex` and `copyOfRange`.

## Deprecations
None

## Removals
None

## Security Patches
None

# 1.4.1 - 2020-06-27

## Additions
* Added `flipIndices` to `ArrayUtil`.
* Added `onTabComplete(CommandContext)` which gives the same environment as the command.

## Changes
* Moved automatic completion of children to `BranchCommand`.

## Deprecations
None

## Removals
* `showHelpOrNoPerms` which was deprecated in 1.3.0.

## Security Patches
None

# 1.4.0 - 2020-06-27

## Additions
* Added `ParameterizedBranchCommand` which flips its child arg and next arg.
* Added `ArrayUtil` which provides some utilities for arrays.

## Changes
* Moved Responses to their own package. Renamed predicates to be clearer.

## Deprecations
None

## Removals
None

## Security Patches
None

# 1.3.10 - 2020-06-27

## Additions
None

## Changes
* Add Gson to the in-game list of libraries.
* Build CommandContext label incrementally.

## Deprecations
None

## Removals
None

## Security Patches
None

# 1.3.9 - 2020-06-27

## Additions
None

## Changes
* ShatteredCore plugin now creates messages properly.
* ShatteredPlugin defaults to creating messages.

## Deprecations
None

## Removals
None

## Security Patches
None

# 1.3.8 - 2020-06-27

## Additions
None

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
None

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
None

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