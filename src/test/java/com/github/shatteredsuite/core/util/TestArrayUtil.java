package com.github.shatteredsuite.core.util;

import org.junit.Assert;
import org.junit.Test;

public class TestArrayUtil {
    @Test
    public void testWithoutIndex() {
        Integer[] arr = {1, 2, 3, 4, 5};
        Integer[] expected = {1, 3, 4, 5};
        Assert.assertArrayEquals(expected, ArrayUtil.withoutIndex(arr, 1));
    }

    @Test
    public void testParseRangeSimple() {
        Integer[] expected = {1, 3, 5};
        Assert.assertArrayEquals(expected, ArrayUtil.parseRange("1,3,5").toArray(new Integer[]{}));
    }

    @Test
    public void testParseRangeRanges() {
        Integer[] expected = {1, 3, 4, 5};
        Assert.assertArrayEquals(expected, ArrayUtil.parseRange("1,3-5").toArray(new Integer[]{}));
    }

    @Test
    public void testCopyOfRange() {
        Integer[] expected = {1, 3, 4, 5};
        Integer[] arr = {1, 2, 3, 4, 5, 6};
        Assert.assertArrayEquals(expected, ArrayUtil.copyOfRange("0,2-4", arr));
    }
}
