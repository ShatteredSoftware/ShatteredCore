package com.github.shatteredsuite.core.data.generic

import com.github.shatteredsuite.core.data.plugin.PluginTypeKey
import com.github.shatteredsuite.core.extension.mapValuesNotNull

/**
 * Stores arbitrary data in a more type-safe way.
 *
 * ## Example:
 * ```kotlin
 * val dataContainer = GenericDataContainer()
 * dataContainer.set("hello-message", "Hello, world!")
 *
 * val message = dataContainer.get("hello-message", String::class.java)
 * println(message) // Prints "Hello, world!"
 *
 * val notMessage = dataContainer.get("hello-message", Int::class.java)
 * println(notMessage) // Prints "null"
 *
 * val message2 = dataContainer.get<String>("hello-message")
 * println(message2) // Prints "Hello, world!"
 * ```
 */
@Suppress("unused") // API Class
class GenericDataStore(private val logFailures: Boolean = true) : MutableDataStore {

    companion object {
        fun of(vararg data: Pair<String, Any>): GenericDataStore {
            val store = GenericDataStore()
            data.forEach { (key, value) ->
                store[key] = value
            }
            return store
        }
    }

    private val valueMap: MutableMap<String, Any> = mutableMapOf()

    override val keys: Set<String> get() = valueMap.keys

    val values: Set<Any> get() = valueMap.values.toSet()

    /**
     * Adds or replaces a value in this container. Null values are ignored.
     */
    override fun <T : Any> put(id: String, value: T?) {
        valueMap[id] = value ?: return
    }

    /**
     * Adds a value to this container if the given key doesn't already exist.
     *
     * @param id The ID to compare against.
     * @param value The value to possibly insert.
     */
    override fun <T : Any> putIfAbsent(id: String, value: T) {
        valueMap.putIfAbsent(id, value)
    }

    /**
     * Pulls a value from this container if it exists and is of the given type, or returns `null` otherwise.
     *
     * @param id The ID to look up.
     * @param cl The class to check for.
     * @return The element contained in this container if it exists and is of the given type, or `null` otherwise.
     */
    override fun <T : Any> get(id: String, cl: Class<T>): T? {
        val value = valueMap[id] ?: return null
        return reflectiveTypeAssertion(value, cl)
    }

    override fun <T : Any> get(pluginTypeKey: PluginTypeKey<T>): T? {
        val value = valueMap[pluginTypeKey.toString()] ?: return null
        return reflectiveTypeAssertion(value, pluginTypeKey.clazz)
    }

    /**
     * Pulls a value from this container if it exists and is of the same type as the default, or returns the default
     * otherwise.
     *
     * @param id The ID to look up.
     * @param def The default value.
     * @return The element contained in this container if it exists and is of the given type, or the default value
     * otherwise.
     */
    override fun <T : Any> getOrDef(id: String, def: T): T {
        val value = valueMap[id] ?: return def
        return reflectiveTypeAssertion(value, def.javaClass) ?: def
    }

    /**
     * Pulls a value from this container regardless of its class.
     *
     * @param id The ID to look up.
     * @return The element contained in this container if it exists.
     */
    override fun getUnsafe(id: String): Any? {
        return valueMap[id]
    }

    override fun remove(id: String) {
        valueMap.remove(id)
    }

    /**
     * Removes all values from this container.
     */
    override fun clear() {
        valueMap.clear()
    }

    /**
     * Useful for doing multiple operations/fetches from the container. Provides the class for you, and provides a nicer
     * interface.
     *
     * @param T The class to mask.
     * @return A masked data container that can only retrieve the given class.
     */
    inline fun <reified T : Any> masked(): MaskedDataContainer<T> {
        return MaskedDataContainer(T::class.java, this)
    }

    /**
     * Reduces this to a map of one class; any values that are not of the given type are not included in the new map.
     *
     * @param cl The class to filter this down to.
     * @return A map of only the given class.
     */
    fun <T : Any> asMapOf(cl: Class<T>): Map<String, T> {
        return valueMap.mapValuesNotNull {
            val v = it.value ?: return@mapValuesNotNull null
            reflectiveTypeAssertion(v, cl)
        }
    }

    /**
     * Performs a type assertion using reflection. Returns null if the assertion fails.
     *
     * @param T The class type to be checking for.
     * @param value The value to check.
     * @param cl The class to check against.
     * @return The value if it's of type cl, otherwise null.
     */
    private fun <T : Any> reflectiveTypeAssertion(value: Any, cl: Class<T>): T? {
        if (cl.isInstance(value)) {
            @Suppress("UNCHECKED_CAST") // I check this right above this, I promise.
            return value as T
        }
        if (logFailures) {
            val trace = Throwable().stackTrace
            System.err.println("Type assertion of $value to ${cl.name} failed: ${value.javaClass.name} cannot be assigned to ${cl.name}.\nStacktrace:\n$trace")
        }
        return null
    }

    inline fun <reified T : Any> asMapOf(): Map<String, T> {
        return asMapOf(T::class.java)
    }
}