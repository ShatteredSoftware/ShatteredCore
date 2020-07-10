package com.github.shatteredsuite.core.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestStringUtil {

    @Test
    public void blankTest() {
        assertTrue(StringUtil.isEmptyOrNull(""), "Blank strings should return true.");
    }

    @Test
    public void nullTest() {
        assertTrue(StringUtil.isEmptyOrNull(null), "Null strings should return true.");
    }

    @Test
    public void fullTest() {
        assertFalse(StringUtil.isEmptyOrNull("Hello!"), "Strings with content should return false.");
    }

    @Test
    public void testFixArgs() {
        String[] arr = new String[]{"\"This", "is\"", "a", "cool", "test", ""};
        String[] expected = new String[]{"This is", "a", "cool", "test", ""};
        String[] res = StringUtil.fixArgs(arr);
        Assertions.assertArrayEquals(expected, res, "Args should be fixed properly");
    }

    @Test
    public void testFixArgsNoTrailing() {
        String[] arr = new String[]{"\"This", "is\"", "a", "cool", "test"};
        String[] expected = new String[]{"This is", "a", "cool", "test"};
        String[] res = StringUtil.fixArgs(arr);
        Assertions.assertArrayEquals(expected, res, "Args should be fixed properly");
    }

    @Test
    public void testFixArgsBlanks() {
        String[] arr = new String[]{};
        String[] expected = new String[]{};
        String[] res = StringUtil.fixArgs(arr);
        Assertions.assertArrayEquals(expected, res, "Args should be fixed properly");
    }
}
