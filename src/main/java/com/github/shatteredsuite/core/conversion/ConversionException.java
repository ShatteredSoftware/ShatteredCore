package com.github.shatteredsuite.core.conversion;

public class ConversionException extends RuntimeException {
    public final String errorKey;
    public final String offender;
    public final String options;

    public ConversionException(String errorKey, String offender) {
        this.errorKey = errorKey;
        this.offender = offender;
        this.options = "";
    }

    public ConversionException(String message, String errorKey, String offender) {
        super(message);
        this.errorKey = errorKey;
        this.offender = offender;
        this.options = "";
    }

    public ConversionException(String message, String errorKey, String offender, String options) {
        super(message);
        this.errorKey = errorKey;
        this.offender = offender;
        this.options = options;
    }

}
