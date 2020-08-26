package com.github.shatteredsuite.core.validation;

import com.github.shatteredsuite.core.conversion.Conversion;
import org.jetbrains.annotations.NotNull;

public interface Validator<T> extends Conversion<String, T> {
    @NotNull
    T validate(@NotNull String param) throws ArgumentValidationException;

    @Override
    default T convert(@NotNull String source) {
        return validate(source);
    }
}
