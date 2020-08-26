package com.github.shatteredsuite.core.config;

import com.google.common.collect.Lists;
import org.bukkit.Material;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TestConfigRecipe {
    @Test
    @DisplayName("empty recipes should not be valid")
    public void testEmptyRecipe() {
        ConfigRecipe recipe = new ConfigRecipe();
        assertThat(recipe.isValid(), is(false));
        assertThat(recipe.getItems(), is(Lists.newArrayList("   ", "   ", "   ")));
        assertThat(recipe.getMapping(), is(anEmptyMap()));
    }

    @Test
    @DisplayName("should be able to find materials and set validity")
    public void testCreation() {
        HashMap<String, String> map = new HashMap<>();
        map.put("S", "STRING");
        map.put("P", "PAPER");
        map.put("C", "COBBLESTONE");
        HashMap<Character, Material> mapping = new HashMap<>();
        mapping.put('S', Material.STRING);
        mapping.put('P', Material.PAPER);
        mapping.put('C', Material.COBBLESTONE);
        List<String> items = Lists.newArrayList("SSS", "PCP", "CCC");
        ConfigRecipe recipe = new ConfigRecipe(items, map);
        assertThat(recipe.getItems(), contains("SSS", "PCP", "CCC"));
        assertThat(recipe.isValid(), is(true));
        assertThat(recipe.getMapping(), is(mapping));
    }

    @Test
    @DisplayName("should ignore strings longer than one")
    public void testInvalidMappingLength() {
        HashMap<String, String> map = new HashMap<>();
        map.put("WW", "WATER");
        map.put("I", "INVALID_MATERIAL");
        List<String> items = Lists.newArrayList("WWW","WIW","WWW");
        ConfigRecipe recipe = new ConfigRecipe(items, map);
        assertThat(recipe.isValid(), is(false));
        assertThat(recipe.getMapping(), is(anEmptyMap()));
    }

    @Test
    @DisplayName("should be able to change rows")
    public void testWithRow() {
        HashMap<String, String> map = new HashMap<>();
        map.put("W", "WATER_BUCKET");
        map.put("I", "IRON_INGOT");
        List<String> items = Lists.newArrayList("WWW","WIW","WWW");
        ConfigRecipe recipe = new ConfigRecipe(items, map);
        assertThat(recipe.isValid(), is(true));
        recipe.withRow(1, "IWI");
        assertThat(recipe.isValid(), is(true));
        assertThat(recipe.getItems(), is(Lists.newArrayList("WWW", "IWI", "WWW")));
    }

    @Test
    @DisplayName("should not be able to change invalid rows")
    public void testWithInvalidRow() {
        HashMap<String, String> map = new HashMap<>();
        map.put("W", "WATER_BUCKET");
        map.put("I", "IRON_INGOT");
        List<String> items = Lists.newArrayList("WWW","WIW","WWW");
        ConfigRecipe recipe = new ConfigRecipe(items, map);
        assertThat(recipe.isValid(), is(true));
        recipe.withRow(-1, "IWI");
        recipe.withRow(3, "IWI");
        assertThat(recipe.isValid(), is(true));
        assertThat(recipe.getItems(), is(Lists.newArrayList("WWW", "WIW", "WWW")));
    }

    @Test
    @DisplayName("should not be valid without any items")
    public void testWithEmptyItems() {
        HashMap<String, String> map = new HashMap<>();
        map.put("W", "WATER_BUCKET");
        map.put("I", "IRON_INGOT");
        List<String> items = Lists.newArrayList();
        ConfigRecipe recipe = new ConfigRecipe(items, map);
        assertThat(recipe.isValid(), is(false));
    }

    @Test
    @DisplayName("should not be valid when items updated to nothing")
    public void testSetNoItems() {
        HashMap<String, String> map = new HashMap<>();
        map.put("W", "WATER_BUCKET");
        map.put("I", "IRON_INGOT");
        List<String> items = Lists.newArrayList();
        ConfigRecipe recipe = new ConfigRecipe(items, map);
        recipe.setItems(items);
        assertThat(recipe.isValid(), is(false));
    }

    @Test
    @DisplayName("should not be valid when items updated to too many items")
    public void testSetTooManyItems() {
        HashMap<String, String> map = new HashMap<>();
        map.put("S", "STRING");
        List<String> items = Lists.newArrayList(" S ", " S ", " S ");
        ConfigRecipe recipe = new ConfigRecipe(items, map);
        // Ensure that the update is the thing doing bad things.
        assertThat(recipe.isValid(), is(true));
        items = Lists.newArrayList(" S ", " S ", " S ", " S ");
        recipe.setItems(items);
        assertThat(recipe.isValid(), is(false));
    }

    @Test
    @DisplayName("should not be valid when items updated to be too long")
    public void testTooLongItems() {
        HashMap<String, String> map = new HashMap<>();
        map.put("S", "STRING");
        List<String> items = Lists.newArrayList("SSS");
        ConfigRecipe recipe = new ConfigRecipe(items, map);
        // Ensure that the update is the thing doing bad things.
        assertThat(recipe.isValid(), is(true));
        items = Lists.newArrayList("SSSS");
        recipe.setItems(items);
        assertThat(recipe.isValid(), is(false));
    }

    @Test
    @DisplayName("should not be valid when items updated to be invalid")
    public void testInvalidItemsUpdate() {
        HashMap<String, String> map = new HashMap<>();
        map.put("S", "STRING");
        List<String> items = Lists.newArrayList("SSS");
        ConfigRecipe recipe = new ConfigRecipe(items, map);
        // Ensure that the update is the thing doing bad things.
        assertThat(recipe.isValid(), is(true));
        items = Lists.newArrayList("SXS");
        recipe.setItems(items);
        assertThat(recipe.isValid(), is(false));
    }

    @Test
    @DisplayName("should not be valid with no bindings")
    public void testWithEmptyBindings() {
        HashMap<String, String> map = new HashMap<>();
        List<String> items = Lists.newArrayList(" W ","WIW"," W ");
        ConfigRecipe recipe = new ConfigRecipe(items, map);
        assertThat(recipe.isValid(), is(false));
    }

    @Test
    @DisplayName("should be vaild with empty spaces")
    public void testWithEmptySpaces() {
        HashMap<String, String> map = new HashMap<>();
        map.put("W", "WATER_BUCKET");
        map.put("I", "IRON_INGOT");
        List<String> items = Lists.newArrayList(" W ","WIW"," W ");
        ConfigRecipe recipe = new ConfigRecipe(items, map);
        assertThat(recipe.isValid(), is(true));
    }
}
