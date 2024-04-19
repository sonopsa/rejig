package com.github.sonopsa.rejig.config;

import com.github.sonopsa.rejig.Rejig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;
import java.util.HashMap;

public class ConfigInstance {
    public static Path BasePath = Path.of("./config/");
    public static String Extension = "json";

    public Path FilePath;

    public HashMap<String, ConfigOption> OptionMap;

    private static final Gson Gson = new GsonBuilder().setPrettyPrinting().create();

    public ConfigInstance(String path) {
        OptionMap = new HashMap<>();
        FilePath = Path.of(path);
    }

    public Path getFullPath() {
        return BasePath.resolve(FilePath + "." + Extension);
    }

    public ConfigOption registerOption(String identifier, ConfigOption option) {
        OptionMap.put(identifier, option);

        return option;
    }

    public ConfigOption getOption(String identifier) {
        return OptionMap.get(identifier);
    }

    public void loadFromFile() {
        String filePath = getFullPath().toString();
        File file = new File(filePath);

        if (!file.exists()) {
            saveToFile();
            return;
        }

        try {
            FileReader fileReader = new FileReader(filePath);
            HashMap<String, Object> load = Gson.fromJson(fileReader, new TypeToken<HashMap<String, Object>>(){}.getType());

            load.forEach((key, value) -> {
                ConfigOption option = getOption(key);

                if (option != null) {
                    option.set(value);
                }
            });

            Rejig.LOGGER.info("Successfully loaded config. (" + filePath + ")");
        } catch (Exception err) {
            Rejig.LOGGER.error("Failed to load config! (" + filePath + ") (" + err.getLocalizedMessage() + ")");
        }
    }

    public void saveToFile() {
        HashMap<String, Object> temporaryMap = new HashMap<>();

        OptionMap.forEach((key, value) -> temporaryMap.put(key, value.get()));

        String jsonContent = Gson.toJson(temporaryMap);
        String filePath = getFullPath().toString();

        try {
            File file = new File(filePath);

            file.getParentFile().mkdirs();
            file.createNewFile();

            FileWriter fileWriter = new FileWriter(filePath, false);
            fileWriter.write(jsonContent);
            fileWriter.close();

            Rejig.LOGGER.info("Successfully saved config. (" + filePath + ")");
        } catch (Exception err) {
            Rejig.LOGGER.error("Failed to save config! (" + filePath + ") (" + err.getLocalizedMessage() + ")");
        }
    }
}
