package com.github.shatteredsuite.core.commands;

import com.github.shatteredsuite.core.validation.ArgumentValidationException;
import com.github.shatteredsuite.core.validation.ChoiceValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

public class TestTabCompleters {
    private List<String> choices = new LinkedList<>();

    @BeforeEach
    public void setUp() {
        choices.add("one");
        choices.add("two");
        choices.add("three");
    }

    @Test
    public void testCompleteFromOptionsValid() {
        List<String> result = TabCompleters.completeFromOptions(new String[]{"t"}, 0, choices);
        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.contains("two"));
        Assertions.assertTrue(result.contains("three"));
    }

    @Test
    public void testCompleteFromOptionsPartial() {
        List<String> result = TabCompleters.completeFromOptions(new String[]{"ree"}, 0, choices);
        Assertions.assertEquals(1, result.size());
        Assertions.assertTrue(result.contains("three"));
    }
}
