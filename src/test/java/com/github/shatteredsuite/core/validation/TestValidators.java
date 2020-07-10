package com.github.shatteredsuite.core.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestValidators {
    @Test
    public void testIntValidator() {
        boolean thrown = false;
        Assertions.assertEquals(1, Validators.integerValidator.validate("1"), 0.1f, "1 should properly be converted.");
        try {
            int y = Validators.integerValidator.validate("x");
        }
        catch (ArgumentValidationException ex) {
            thrown = true;
            Assertions.assertEquals("invalid-integer", ex.errorKey, "Should have a correct error key when invalid.");
            Assertions.assertEquals("x", ex.offender, "Should have a correct offender when invalid.");
        }
        Assertions.assertTrue(thrown, "Should not be able to convert x to an int.");
    }

    @Test
    public void testFloatValidator() {
        boolean thrown = false;
        Assertions.assertEquals(1.3f, Validators.floatValidator.validate("1.3"), 0.1f, "1.3 should properly be converted.");
        Assertions.assertEquals(1.0f, Validators.floatValidator.validate("1"), 0.1f, "1 should properly be converted.");
        try {
            float y = Validators.floatValidator.validate("x");
        }
        catch (ArgumentValidationException ex) {
            thrown = true;
            Assertions.assertEquals("invalid-float", ex.errorKey, "Should have a correct error key when invalid.");
            Assertions.assertEquals("x", ex.offender, "Should have a correct offender when invalid.");
        }
        Assertions.assertTrue(thrown, "Should not be able to convert x to a float.");
    }

    @Test
    public void testDoubleValidator() {
        boolean thrown = false;
        Assertions.assertEquals(1.3d, Validators.doubleValidator.validate("1.3"), 0.1f, "1.3 should properly be converted.");
        Assertions.assertEquals(1.0d, Validators.doubleValidator.validate("1"), 0.1f, "1 should properly be converted.");
        try {
            double y = Validators.doubleValidator.validate("x");
        }
        catch (ArgumentValidationException ex) {
            thrown = true;
            Assertions.assertEquals("invalid-double", ex.errorKey, "Should have a correct error key when invalid.");
            Assertions.assertEquals("x", ex.offender, "Should have a correct offender when invalid.");
        }
        Assertions.assertTrue(thrown, "Should not be able to convert x to a float.");
    }
}
