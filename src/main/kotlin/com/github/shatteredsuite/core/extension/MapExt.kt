package com.github.shatteredsuite.core.extension

fun <K, V> MutableMap<K, MutableSet<V>>.addSafe(key: K, value: V) {
    val target = getSafe(key) { mutableSetOf() }
    target.add(value)
}

fun <K, N, V> MutableMap<K, MutableMap<N, V>>.addSafe(key: K, nested: N, value: V) {
    val target = getSafe(key) { mutableMapOf() }
    target[nested] = value
}

fun <K, V> MutableMap<K, V>.ensure(key: K, init: () -> V) {
    if (!this.containsKey(key)) {
        this[key] = init()
    }
}

fun <K, V> MutableMap<K, V>.getSafe(key: K, init: () -> V): V {
    val value = this[key] ?: init()
    if (!this.containsKey(key)) {
        this[key] = value
    }
    return value
}

inline fun <reified T, K, V> Map<K, *>.getTyped(key: K): T? where T : V {
    val value = this[key] ?: return null
    if (T::class.java.isInstance(value)) {
        return value as T
    }
    return null
}

fun <K, V, R> Map<K, V?>.mapValuesNotNull(fn: (Map.Entry<K, V?>) -> R?): Map<K, R> {
    @Suppress("UNCHECKED_CAST") // We filter for nulls so we should be OK to cast off the R?
    return this.mapValues(fn).filterValues { it != null } as Map<K, R>
}