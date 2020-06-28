package com.github.shatteredsuite.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArrayUtil {
    private static final Pattern SEPARATOR = Pattern.compile(",");
    private static final Pattern RANGE = Pattern.compile("(\\d+)-(\\d+)");

    public static <T> void flipIndices(T[] array, int index1, int index2) {
        T tmp = array[index2];
        array[index2] = array[index1];
        array[index1] = tmp;
    }

    public static <T> T[] withoutIndex(T[] array, int index) {
        List<T> results = new ArrayList<>();
        results.addAll(Arrays.asList(array).subList(0, index));
        results.addAll(Arrays.asList(array).subList(index + 1, array.length));
        //noinspection unchecked
        return (T[]) results.toArray();
    }

    public static <T> T[] copyOfRange(String range, T[] array) {
        List<Integer> indices = parseRange(range);
        List<T> results = new ArrayList<>();
        for (int index : indices) {
            results.add(array[index]);
        }
        //noinspection unchecked
        return (T[]) results.toArray();
    }

    public static List<Integer> parseRange(String range) {
        String[] parts = SEPARATOR.split(range);
        List<Integer> results = new ArrayList<>();
        for (String part : parts) {
            Matcher matcher = RANGE.matcher(part);
            if (matcher.matches()) { // This is a range, i.e. 1-3.
                int low = Integer.parseInt(matcher.group(1));
                int high = Integer.parseInt(matcher.group(2));
                for (int i = low; i <= high; i++) {
                    results.add(i);
                }
            }
            else { // This is a single digit.
                results.add(Integer.parseInt(part));
            }
        }
        return results;
    }
}
