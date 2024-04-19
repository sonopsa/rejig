package com.github.sonopsa.rejig.config;

public class ConfigOption {
    private Object Value;
    private final Object Default;

    public ConfigOption(Object defaultValue) {
        Default = defaultValue;
        Value = Default;
    }

    public void set(Object value) {
        this.Value = value;
    }

    public Object get() {
        return Value != null ? Value : Default;
    }
}
