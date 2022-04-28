package com.shop.pages.configuration.userConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.models.User;
import com.shop.pages.handlers.DataHandler;
import com.shop.pages.handlers.FileHandler;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class UserFactory extends DataHandler {
    private static Logger log = LoggerFactory.getLogger(UserFactory.class);
    private FileHandler fileHandler;
    private static final String userJsonPath = "src/main/resources/files/accounts.json";

    public UserFactory(WebDriver driver) {
        super(driver);
        fileHandler = new FileHandler(userJsonPath);
    }

    public User getRandomUser() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getRandomBirthday());
        return new User.UserBuilder()
                .socialTitle(getRandomSocialTitle())
                .firstName(getRandomFirstName())
                .lastName(getRandomLastName())
                .email(getRandomEmail())
                .password(getRandomPassword(5, 10, true))
                .month(String.valueOf(cal.get(Calendar.MONTH) + 1))
                .day(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)))
                .year(String.valueOf(cal.get(Calendar.YEAR)))
                .receiveOffers(getRandomBoolean())
                .dataPrivacy(true)
                .singUpNewsletter(getRandomBoolean())
                .acceptPolicy(true)
                .build();
    }

    public User getAlreadyRegisteredUser() {
        File file = fileHandler.getFile();
        if (file.length() == 0) getRandomUser();
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(file, User.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public FileHandler getFileHandler() {
        return fileHandler;
    }
}