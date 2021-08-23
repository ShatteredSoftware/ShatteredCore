package com.github.shatteredsuite.core.util;

import com.github.shatteredsuite.core.data.adapter.LocationAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Location;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToCompressingWhiteSpace;
import static org.hamcrest.Matchers.is;

public class TestLocationSerializer {
    public static Gson gson;

    @BeforeAll
    public static void setUp() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Location.class, new LocationAdapter());
        builder.registerTypeAdapter(Location.class, new LocationDeserializer());
        gson = builder.create();
    }

    @Test
    public void testDeserialize() {
        Location loc = new Location(null, 12, 12, 12, 90.0F, 90.0F);
        String json = gson.toJson(loc);
        assertThat(json, equalToCompressingWhiteSpace("{\"x\":12.0,\"y\":12.0,\"z\":12.0,\"pitch\":90.0,\"yaw\":90.0}"));
    }

    @Test
    public void testNullDeserialize() {
        String json = gson.toJson((Location) null);
        assertThat(json, equalToCompressingWhiteSpace("null"));
    }

    @Test
    public void testSerialize() {
        Location loc = gson.fromJson( "{\"x\":12.0,\"y\":12.0,\"z\":12.0,\"pitch\":90.0,\"yaw\":90.0}", Location.class);
        assertThat(loc.getX(), is(12.0));
        assertThat(loc.getY(), is(12.0));
        assertThat(loc.getZ(), is(12.0));
        assertThat(loc.getPitch(), is(90.0f));
        assertThat(loc.getYaw(), is(90.0f));
    }
}
