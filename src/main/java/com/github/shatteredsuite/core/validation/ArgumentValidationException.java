package com.github.shatteredsuite.core.validation;

import com.github.shatteredsuite.core.conversion.ConversionException;

public class ArgumentValidationException extends ConversionException {
    public final ValidationErrorType type;

    public ArgumentValidationException(ValidationErrorType type, String errorKey, String offender) {
        super(errorKey, offender);
        this.type = type;
    }

    public ArgumentValidationException(String message, ValidationErrorType type, String errorKey, String offender) {
        super(message, errorKey, offender);
        this.type = type;
    }

    public ArgumentValidationException(String message, ValidationErrorType type, String errorKey, String offender, String options) {
        super(message, errorKey, offender, options);
        this.type = type;
    }

    public enum ValidationErrorType {
        NOT_ENOUGH_ARGS,
        INVALID_FORMAT
    }
}
