package com.github.shatteredsuite.core.validation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class TestChoiceValidator {
    private List<String> choices = new LinkedList<>();
    private ChoiceValidator validator;

    @Before
    public void setUp() {
        choices.add("one");
        choices.add("two");
        choices.add("three");
        validator = new ChoiceValidator(choices);
    }

    @Test(expected = ArgumentValidationException.class)
    public void testChoiceValidatorInvalidChoice() {
        validator.validate("four");
    }

    @Test
    public void testChoiceValidator() {
        boolean thrown = false;
        try {
            validator.validate("three");
        }
        catch (ArgumentValidationException ex) {
            thrown = true;
        }
        Assert.assertFalse(thrown);
    }
}
