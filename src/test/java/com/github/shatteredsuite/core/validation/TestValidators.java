package com.github.shatteredsuite.core.validation;

import org.junit.Assert;
import org.junit.Test;

public class TestValidators {
    @Test
    public void testIntValidator() {
        boolean thrown = false;
        Assert.assertEquals("1 should properly be converted.", 1, Validators.integerValidator.validate("1"), 0);
        try {
            int y = Validators.integerValidator.validate("x");
        }
        catch (ArgumentValidationException ex) {
            thrown = true;
            Assert.assertEquals("Should have a correct error key when invalid.", "invalid-integer", ex.errorKey);
            Assert.assertEquals("Should have a correct offender when invalid.", "x", ex.offender);
        }
        Assert.assertTrue("Should not be able to convert x to an int.", thrown);
    }

    @Test
    public void testFloatValidator() {
        boolean thrown = false;
        Assert.assertEquals("1.3 should properly be converted.", 1.3f, Validators.floatValidator.validate("1.3"), 0.1f);
        Assert.assertEquals("1 should properly be converted.", 1.0f, Validators.floatValidator.validate("1"), 0.1f);
        try {
            float y = Validators.floatValidator.validate("x");
        }
        catch (ArgumentValidationException ex) {
            thrown = true;
            Assert.assertEquals("Should have a correct error key when invalid.", "invalid-float", ex.errorKey);
            Assert.assertEquals("Should have a correct offender when invalid.", "x", ex.offender);
        }
        Assert.assertTrue("Should not be able to convert x to a float.", thrown);
    }

    @Test
    public void testDoubleValidator() {
        boolean thrown = false;
        Assert.assertEquals("1.3 should properly be converted.", 1.3d, Validators.doubleValidator.validate("1.3"), 0.1f);
        Assert.assertEquals("1 should properly be converted.", 1.0d, Validators.doubleValidator.validate("1"), 0.1f);
        try {
            double y = Validators.doubleValidator.validate("x");
        }
        catch (ArgumentValidationException ex) {
            thrown = true;
            Assert.assertEquals("Should have a correct error key when invalid.", "invalid-double", ex.errorKey);
            Assert.assertEquals("Should have a correct offender when invalid.", "x", ex.offender);
        }
        Assert.assertTrue("Should not be able to convert x to a float.", thrown);
    }
}
