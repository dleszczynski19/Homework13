package com.shop.models.yaml;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.LinkedHashMap;
import java.util.Map;

public class YamlFile {
    private Map<String, Object> properties = new LinkedHashMap<>();
    private String envName;


    public YamlFile() {
    }

    public String getEnvName() {
        return envName;
    }

    @JsonAnySetter
    void setProperties(String key, Object value) {
        properties.put(key, value);
    }

    @JsonAnyGetter
    public Map<String, Object> getProperties() {
        return properties;
    }
}