package com.github.sonopsa.rejig.config;

import com.github.sonopsa.rejig.Rejig;

import java.nio.file.Path;
import java.util.Map;

public class ConfigHandler {
    public static class Item {
        public String Identifier;
        public Object Value;

        public Object Default;

        public Item(String id) {
            Identifier = id;
        }

        public Item setDefault(Object value) {
            Default = value;
            Value = Default;

            return this;
        }
    }

    private Path path;

    private Map<String, Item> Items;

    public Item register(Item item) {
        if (Items.containsKey(item.Identifier)) {
            Rejig.LOGGER.warn("Item with key \"" + item.Identifier + "\" already present! Overriding...");
        }

        Items.put(item.Identifier, item);
        return Items.get(item.Identifier);
    }

    public Item register(String id, Object defaultValue) {
        return new Item(id).setDefault(defaultValue);
    }

    public Item get(String id) {
        return Items.get(id);
    }

    public Object getValue(String id) {
        return get(id).Value;
    }
}