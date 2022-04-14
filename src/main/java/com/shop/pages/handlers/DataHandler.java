package com.shop.pages.handlers;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import com.shop.pages.configuration.WebElementHelper;
import org.openqa.selenium.WebDriver;

import java.util.Date;
import java.util.Locale;

public class DataHandler extends WebElementHelper {
    private final String[] socialTitleArray = new String[]{"Mr.", "Mrs."};
    private Faker faker;

    public DataHandler(WebDriver driver) {
        super(driver);
        faker = new Faker();
    }

    public String getRandomSocialTitle() {
        //faker.name().prefix()); returns a name prefix such as Mr., Mrs., Ms., Miss, or Dr. || W związku z tym tablica wydaje się łatwiejsza
        return socialTitleArray[faker.number().numberBetween(0, 1)];
    }

    public String getRandomFirstName() {
        return faker.name().firstName();
    }

    public String getRandomLastName() {
        return faker.name().lastName();
    }

    public String getRandomEmail() {
        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en-GB"), new RandomService());
        return fakeValuesService.bothify("?????###@gmail.com");
    }

    public String getRandomPassword(int minLength, int maxLength, boolean isUppercase) {
        return faker.internet().password(minLength, maxLength, isUppercase);
    }

    public Date getRandomBirthday() {
        return faker.date().birthday();
    }

    public boolean getRandomBoolean() {
        return faker.random().nextBoolean();
    }

    public int getRandomNumber(int from, int to) {
        return faker.random().nextInt(from, to);
    }
}
