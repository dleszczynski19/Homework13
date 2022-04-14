package com.shop.models.yaml;

import java.util.ArrayList;
import java.util.List;

public class Environment {
    private YamlFile integrate;
    private YamlFile test;
    private List<YamlFile> envList = new ArrayList<>();

    public List<YamlFile> getEnvList() {
        envList.add(getIntegrate());
        envList.add(getTest());
        return envList;
    }

    public YamlFile getIntegrate() {
        return integrate;
    }

    public YamlFile getTest() {
        return test;
    }
}