package com.github.shatteredsuite.core.util;

public interface Provider<T> {
    T get();

    default Provider<T> of(T t) {
        return () -> t;
    }
}
