package com.github.shatteredsuite.core.util;

import java.util.Map;

public interface Lookup<K, V> {
    V get(K key);

    static <K, V> Lookup<K, V> from(Map<K, V> map) {
        return map::get;
    }
}
