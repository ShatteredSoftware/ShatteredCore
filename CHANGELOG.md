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