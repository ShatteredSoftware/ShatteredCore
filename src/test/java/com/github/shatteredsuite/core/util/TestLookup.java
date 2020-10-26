package com.github.shatteredsuite.core.util;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TestLookup {

    @Test
    public void testFrom() {
        HashMap<String, String> map = new HashMap<>();
        map.put("testMsg", "Hello!");

        Lookup<String, String> lookup = Lookup.from(map);
        assertThat(lookup.get("testMsg"), is("Hello!"));
    }
}
