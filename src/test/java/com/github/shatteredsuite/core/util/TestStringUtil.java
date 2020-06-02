package com.github.shatteredsuite.core.util;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class TestStringUtil {

    @Test
    public void blankTest() {
        assertTrue("Blank strings should return true.", StringUtil.isEmptyOrNull(""));
    }

    @Test
    public void nullTest() {
        assertTrue("Null strings should return true.", StringUtil.isEmptyOrNull(null));
    }

    @Test
    public void fullTest() {
        assertFalse("Strings with content should return false.", StringUtil.isEmptyOrNull("Hello!"));
    }
}
