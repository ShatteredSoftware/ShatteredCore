package com.github.shatteredsuite.core.util;

public interface Provider<T> {
    T get();

    static <K> Provider<K> of(K t) {
        return () -> t;
    }
}
