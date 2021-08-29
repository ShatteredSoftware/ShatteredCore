package com.github.shatteredsuite.core.data.generic

interface MutableDataStore : DataStore {

    /**
     * Adds or replaces a value in this container. Null values are ignored.
     */
    fun <T : Any> put(id: String, value: T?)

    /**
     * Adds a value to this container if the given key doesn't already exist.
     *
     * @param id The ID to compare against.
     * @param value The value to possibly insert.
     */
    fun <T : Any> putIfAbsent(id: String, value: T)

    fun remove(id: String)

    /**
     * Removes all values from this container.
     */
    fun clear()

    operator fun set(s: String, value: Any) {
        this.put(s, value)
    }
}