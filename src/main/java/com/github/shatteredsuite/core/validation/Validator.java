package com.github.shatteredsuite.core.validation;

import org.jetbrains.annotations.NotNull;

public interface Validator<T> {
    @NotNull T validate(@NotNull String param) throws ArgumentValidationException;
}
