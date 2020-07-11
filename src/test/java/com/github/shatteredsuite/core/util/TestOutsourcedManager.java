package com.github.shatteredsuite.core.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;

public class TestOutsourcedManager {
    private static OutsourcedManager<IdentifiedString> manager;
    private static final HashMap<String, IdentifiedString> source = new HashMap<>();

    @BeforeAll
    public static void setUp() {
        manager = new OutsourcedManager<>();
        manager.addSource("tEstnS", new ExternalProvider<IdentifiedString>() {
            @NotNull
            @Override
            public Iterable<IdentifiedString> all() {
                return source.values();
            }

            @Nullable
            @Override
            public IdentifiedString get(@NotNull String id) {
                return source.get(id);
            }

            @Override
            public boolean has(@NotNull String id) {
                return source.containsKey(id);
            }

            @NotNull
            @Override
            public Iterable<String> keys() {
                return source.keySet();
            }
        });
        source.put("tval", new IdentifiedString("tval", "Test Value"));
        manager.register(new IdentifiedString("nval", "New Value"));
        manager.register(new IdentifiedString("unnamespaced:value", "Unnamespaced Value"));
        manager.register(new IdentifiedString("testns:value", "Fake Test Namespace Value"));
    }

    @Test
    public void testHas() {
        assertThat(manager.has("testns:tval"), is(true));
        assertThat(manager.has("nval"), is(true));
    }

    @Test
    public void testHasUnnamespaced() {
        assertThat(manager.has("unnamespaced:value"), is(true));
    }

    @Test
    public void testHasNotInNamespace() {
        assertThat(manager.has("testns:value"), is(true));
    }

    @Test
    public void testHasInvalidNamespaced() {
        assertThat(manager.has("testns:invalid"), is(false));
    }

    @Test
    public void testHasNonexistentNamespaced() {
        assertThat(manager.has("invalid:invalid"), is(false));
    }

    @Test
    public void testGetNamespaced() {
        IdentifiedString str = manager.get("testns:tval");
        assertThat(str, not(nullValue()));
        assertThat(str.getValue(), equalTo("Test Value"));
    }

    @Test
    public void testGetInternal() {
        IdentifiedString str = manager.get("nval");
        assertThat(str, not(nullValue()));
        assertThat(str.getValue(), equalTo("New Value"));
    }

    @Test
    public void testGetUnnamespaced() {
        IdentifiedString str = manager.get("unnamespaced:value");
        assertThat(str, not(nullValue()));
        assertThat(str.getValue(), equalTo("Unnamespaced Value"));
    }

    @Test
    public void testGetNotInNamespace() {
        IdentifiedString str = manager.get("testns:value");
        assertThat(str, not(nullValue()));
        assertThat(str.getValue(), equalTo("Fake Test Namespace Value"));
    }

    @Test
    public void testInvalidNamespace() {
        IdentifiedString str = manager.get("invalid:invalid");
        assertThat(str, is(nullValue()));
    }

    @Test
    public void testInvalidUnnamespaced() {
        IdentifiedString str = manager.get("invalid");
        assertThat(str, is(nullValue()));
    }

    @Test
    public void testGetIds() {
        Iterable<String> ids = manager.getIds();
        assertThat(ids, containsInAnyOrder("testns:tval", "testns:value", "nval", "unnamespaced:value"));
    }

    @Test
    public void testGetAll() {
        Iterable<IdentifiedString> items = manager.getAll();
        assertThat(items, containsInAnyOrder(new IdentifiedString("tval", "Test Value"),
                new IdentifiedString("nval", "New Value"),
                new IdentifiedString("testns:value", "Fake Test Namespace Value"),
                new IdentifiedString("unnamespaced:value", "Unnamespaced Value")));
    }
}