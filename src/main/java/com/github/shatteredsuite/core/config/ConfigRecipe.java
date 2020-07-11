package com.github.shatteredsuite.core.config;

import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.*;
import java.util.logging.Logger;

@SerializableAs("ConfigRecipe")
public class ConfigRecipe implements ConfigurationSerializable {
    private List<String> items;
    private Map<Character, Material> mapping;
    private transient boolean valid;

    public ConfigRecipe() {
        this.items = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            items.add("   ");
        }
        this.mapping = new HashMap<>();
        this.valid = false;
    }

    public ConfigRecipe(List<String> items, Map<String, String> mapping) {
        this.items = items;
        this.valid = checkValidity(items, mapping);
        this.mapping = generateMapping(mapping);
    }

    @SuppressWarnings("unchecked")
    public ConfigRecipe(Map<String, Object> map) {
        this.items = ConfigUtil.getIfValid(map, "items", ArrayList.class, new ArrayList<String>());
        LinkedHashMap<String, String> mapping = ConfigUtil.getIfValid(map, "mapping", LinkedHashMap.class, new LinkedHashMap<String, String>());
        this.mapping = generateMapping(mapping);
        this.valid = checkValidity(items, mapping);
    }

    public void withItem(char id, Material type) {
        this.mapping.put(id, type);
        checkVailidity();
    }

    public void withRow(int rowNumber, String row) {
        if (rowNumber < 0 || rowNumber >= 3) {
            return;
        }
        this.items.set(rowNumber, row);
        checkVailidity();
    }

    private boolean checkValidity(List<String> items, Map<String, String> mapping) {
        boolean isValid = true;
        if (items.size() < 1) {
            isValid = false;
        }
        if (isValid) {
            for (String s : mapping.keySet()) {
                for (char c : s.toCharArray()) {
                    if (!mapping.containsKey(String.valueOf(c)) && c != ' ') {
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

    private void checkVailidity() {
        if (this.items.size() < 1) {
            this.valid = false;
        }
        for (char c : mapping.keySet()) {
            if (!mapping.containsKey(c) && c != ' ') {
                this.valid = false;
                break;
            }
        }
    }

    private LinkedHashMap<Character, Material> generateMapping(Map<String, String> mapping) {
        LinkedHashMap<Character, Material> result = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : mapping.entrySet()) {
            String key = entry.getKey();
            String mat = entry.getValue();
            if (key.length() != 1) {
                continue;
            }
            char c = key.toCharArray()[0];
            Material m = Material.matchMaterial(mat);
            if (m == null) {
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

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
        checkVailidity();
    }

    public Map<Character, Material> getMapping() {
        return mapping;
    }

    public void setMapping(Map<Character, Material> mapping) {
        this.mapping = mapping;
        checkVailidity();
    }

    public boolean isValid() {
        return valid;
    }
}
