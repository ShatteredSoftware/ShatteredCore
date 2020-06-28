package com.github.shatteredsuite.core.validation;

public class ArgumentValidationException extends RuntimeException {
    public final ValidationErrorType type;
    public final String errorKey;
    public final String offender;
    public final String options;

    public ArgumentValidationException(ValidationErrorType type, String errorKey, String offender) {
        this.type = type;
        this.errorKey = errorKey;
        this.offender = offender;
        this.options = "";
    }

    public ArgumentValidationException(String message, ValidationErrorType type, String errorKey, String offender) {
        super(message);
        this.type = type;
        this.errorKey = errorKey;
        this.offender = offender;
        this.options = "";
    }

    public ArgumentValidationException(String message, ValidationErrorType type, String errorKey, String offender, String options) {
        super(message);
        this.type = type;
        this.errorKey = errorKey;
        this.offender = offender;
        this.options = options;
    }

    public enum ValidationErrorType {
        NOT_ENOUGH_ARGS,
        TOO_MANY_ARGS,
        INVALID_FORMAT
    }
}
