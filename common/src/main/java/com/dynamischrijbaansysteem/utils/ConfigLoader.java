package com.dynamischrijbaansysteem.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static final String CONFIG_FILE = "config.properties";
    private Properties props = new Properties();

    public ConfigLoader() {
        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            props.load(input);
        } catch (IOException e) {
            System.out.println("Config file not found. Loading defaults.");
        }
    }
    public String getProperty(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }
}
