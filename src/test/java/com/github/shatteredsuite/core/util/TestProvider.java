package com.github.shatteredsuite.core.util;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TestProvider {
    @Test
    public void testOf() {
        Provider<String> provider = Provider.of("Hello");

        assertThat(provider.get(), is("Hello"));
    }
}
