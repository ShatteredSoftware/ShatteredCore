package com.github.shatteredsuite.core.config;

import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class ConfigRecipe implements ConfigurationSerializable {
    public final List<String> items;
    public final Map<Character, Material> mapping;
    public final boolean valid;

    public ConfigRecipe(List<String> items, Map<String, String> mapping) {
        this.items = items;
        this.valid = checkValidity(items, mapping);
        this.mapping = generateMapping(mapping);
    }

    @SuppressWarnings("unchecked")
    public ConfigRecipe(Map<String, Object> map) {
        this.items =  ConfigUtil.getIfValid(map, "items", List.class, new ArrayList<String>());
        LinkedHashMap<String, String> mapping = ConfigUtil.getIfValid(map, "mapping", LinkedHashMap.class, new LinkedHashMap<String, String>());
        this.mapping = generateMapping(mapping);
        this.valid = checkValidity(items, mapping);
    }

    private boolean checkValidity(List<String> items, Map<String, String> mapping) {
        boolean isValid = true;
        if(items.size() < 1) {
            isValid = false;
        }
        if(isValid) {
            for(String s : mapping.keySet()) {
                for(char c : s.toCharArray()) {
                    if (!mapping.containsKey(String.valueOf(c))) {
                        isValid = false;
                        Logger.getLogger("ShatteredUtilities").severe("Invalid mapping in config. Trying to use " + c
                            + " in a recipe where it is not defined.");
                        break;
                    }
                }
            }
        }
        return isValid;
    }

    private LinkedHashMap<Character, Material> generateMapping(Map<String, String> mapping) {
        LinkedHashMap<Character, Material> result = new LinkedHashMap<>();
        for(Map.Entry<String, String> entry : mapping.entrySet()) {
            String key = entry.getKey();
            String mat = entry.getValue();
            if(key.length() != 1) {
                continue;
            }
            char c = key.toCharArray()[0];
            Material m = Material.matchMaterial(mat);
            if(m == null) {
                Logger.getLogger("ShatteredUtilities").severe("Invalid material in config: " + mat);
                continue;
            }
            result.put(c, m);
        }
        return result;
    }

    @Override
    public Map<String, Object> serialize() {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("items", items);
        map.put("mapping", mapping);
        return map;
    }
}
