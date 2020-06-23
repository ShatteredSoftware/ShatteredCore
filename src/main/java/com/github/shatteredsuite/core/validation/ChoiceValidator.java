package com.github.shatteredsuite.core.validation;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ChoiceValidator implements Validator<String> {
    private final List<String> choices;

    public ChoiceValidator(List<String> choices) {
        this.choices = choices;
    }

    @NotNull
    @Override
    public String validate(@NotNull String param) throws ArgumentValidationException {
        if(!choices.contains(param)) {
            String options = String.join(", ", choices);
            throw new ArgumentValidationException("Invalid choice. Choices are: " + options, ArgumentValidationException.ValidationErrorType.INVALID_FORMAT, "invalid-choice", param, options);
        }
        return param;
    }
}
