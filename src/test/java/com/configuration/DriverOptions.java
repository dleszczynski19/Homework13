package com.configuration;

public enum DriverOptions {
    MAXIMIZE("start-maximized"), HEADLESS("headless");

    private String option;

    DriverOptions(String option){
        this.option = option;
    }

    public String getOption() {
        return option;
    }
}
