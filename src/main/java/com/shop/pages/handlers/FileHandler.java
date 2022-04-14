package com.shop.pages.handlers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.models.User;
import com.shop.pages.configuration.userConfiguration.UserFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {
    private static Logger log = LoggerFactory.getLogger(UserFactory.class);
    private String filePath;

    public FileHandler(String filePath) {
        this.filePath = filePath;
    }

    public void addUserToFile(User user) {
        try {
            FileWriter fileWriter = new FileWriter(createFile(), true);
            ObjectMapper mapper = new ObjectMapper();
            JsonGenerator generator = mapper.getFactory().createGenerator(fileWriter);
            mapper.writeValue(generator, user);
            log.info("Add user with email " + user.getEmail() + " to json file");
        } catch (IOException e) {
            e.printStackTrace();
            log.warn("Can't add user to file!");
        }
    }

    public File getFile() {
        return new File(filePath);
    }

    private File createFile() {
        File file = getFile();
        try {
            if (file.createNewFile()) {
                log.info("File created: " + file.getName());
            } else {
                log.info("File already exists: " + file.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.warn("Can't create file!");
        }
        return file;
    }
}
