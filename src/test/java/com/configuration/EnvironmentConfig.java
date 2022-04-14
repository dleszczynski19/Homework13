package com.configuration;

import com.shop.models.yaml.YamlFile;
import com.shop.pages.configuration.YamlReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class EnvironmentConfig {
    private static Logger log = LoggerFactory.getLogger(EnvironmentConfig.class);

    private final YamlReader yamlReader;
    private final String environmentName;
    private List<YamlFile> listOfEnvironments;
    private DriverFactory driverFactory;

    public EnvironmentConfig() {
        yamlReader = new YamlReader();
        listOfEnvironments = yamlReader.getConfigEnv().getEnvironments().getEnvList();
        environmentName = yamlReader.getConfigEnv().getCurrentEnv();
        log.info("--------------CONFIG-----------------");
        log.info("Environment name: " + environmentName);
        driverFactory = new DriverFactory(yamlReader.getConfigEnv().getBrowser());
        setActiveConfigProperties();
    }

    public static EnvironmentConfig getInstance() {
        return EnvironmentConfig.EnvironmentPropertySingleton.INSTANCE;
    }

    private void setActiveConfigProperties() {
        listOfEnvironments.stream().filter(env -> env.getEnvName().equals(environmentName))
                .map(YamlFile::getProperties)
                .forEach(a -> a.forEach((key, value) -> System.setProperty(key, value.toString()))
                );
    }

    public DriverFactory getBrowserEnvironment() {
        return driverFactory;
    }

    private static class EnvironmentPropertySingleton {
        private static final EnvironmentConfig INSTANCE = new EnvironmentConfig();
    }
}
