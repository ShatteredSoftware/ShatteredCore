package com.github.shatteredsuite.core.util;

import java.util.function.Supplier;

public interface Provider<T> {
    T get();

    static <K> Provider<K> of(K t) {
        return () -> t;
    }

    static <K> Provider<K> of(Supplier<K> supplier) {
        return supplier::get;
    }
}
