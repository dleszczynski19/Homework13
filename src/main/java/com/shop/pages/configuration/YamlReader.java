package com.shop.pages.configuration;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.shop.models.yaml.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class YamlReader {
    private static Logger log = LoggerFactory.getLogger(YamlReader.class);
    private Config configEnv;

    public YamlReader() {
        configEnv = getYaml(getConfigFile(), Config.class);
    }

    public Config getConfigEnv() {
        return configEnv;
    }

    public File getConfigFile() {
        return new File(java.util.Objects.requireNonNull(Thread.currentThread()
                .getContextClassLoader()
                .getResource("configTest.yaml")).getFile());
    }

    public final <T> T getYaml(File file, Class<T> className) {
        try {
            YAMLMapper mapper = new YAMLMapper();
            return mapper.readValue(file, className);
        } catch (IOException e) {
            log.error("Can't read value", className, e);
            return null;
        }
    }
}