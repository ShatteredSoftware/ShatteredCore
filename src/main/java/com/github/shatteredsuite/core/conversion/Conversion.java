package com.github.shatteredsuite.core.conversion;

public interface Conversion<S, T> {
    T convert(S source);
}
