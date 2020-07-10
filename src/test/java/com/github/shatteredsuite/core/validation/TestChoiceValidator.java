package com.github.shatteredsuite.core.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

public class TestChoiceValidator {
    private List<String> choices = new LinkedList<>();
    private ChoiceValidator validator;

    @BeforeEach
    public void setUp() {
        choices.add("one");
        choices.add("two");
        choices.add("three");
        validator = new ChoiceValidator(choices);
    }

    @Test
    public void testChoiceValidatorInvalidChoice() {
        Assertions.assertThrows(ArgumentValidationException.class, () -> validator.validate("four"));
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
        Assertions.assertFalse(thrown);
    }
}
