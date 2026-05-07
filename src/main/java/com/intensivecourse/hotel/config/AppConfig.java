package com.intensivecourse.hotel.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {

    private final String configFile = "main.properties";
    private final Properties properties;

    public AppConfig(){
        this.properties = new Properties();
        try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream(configFile)){
            this.properties.load(inputStream);
        }catch (IOException e){
            throw new RuntimeException("Failed to load config file: " + this.configFile, e);
        }
    }

    public AppConfig(String fileName){
        this.properties = new Properties();
        try(FileInputStream fis = new FileInputStream(fileName)){
            this.properties.load(fis);
        }catch (IOException e){
            throw new RuntimeException("Failed to load config file: " + fileName, e);
        }
    }

    public String getProperty(String key){
        return this.properties.getProperty(key);
    }

    public int getIntProperty(String key){
        return Integer.parseInt(this.properties.getProperty(key));
    }

    public <T extends Enum<T>> T getEnumProperty(String key, Class<T> enumType){
        String value = properties.getProperty(key);
        return Enum.valueOf(enumType, value);
    }

    public boolean getBoolProperty(String key){
        return Boolean.parseBoolean(properties.getProperty(key));
    }
}
