package com.shop.models.yaml;

public class Config {
    private Environment environments;
    private Browser browser;
    private String currentEnv;

    public Environment getEnvironments() {
        return environments;
    }

    public Browser getBrowser() {
        return browser;
    }

    public String getCurrentEnv() {
        return currentEnv;
    }
}