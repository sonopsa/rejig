package com.github.sonopsa.rejig.config;

public class ConfigHolder {
    public ConfigInstance Config;

    public ConfigHolder(String path) {
        Config = new ConfigInstance(path);

        this.createOptions();
    }

    public void createOptions() { }
}
