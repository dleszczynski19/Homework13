package com.shop.pages.handlers;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import com.shop.pages.helpers.WebElementHelper;
import org.apache.commons.text.WordUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DataHandler extends WebElementHelper {
    private final String[] socialTitleArray = new String[]{"MR", "MRS"};
    private final String[] countryArray = new String[]{"Poland", "United States"};
    private final String[] shippingMethodArray = new String[]{"TesterSii", "My carrier"};
    private String country;
    private String state;
    private String stateAbbr;
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

    public double getPercentOfNumber(double firstNumber, double secondNumber) {
        return (secondNumber / firstNumber) * 100;
    }

    public String getRandomAddress() {
        return faker.address().streetAddress();
    }

    public String getRandomCity() {
        return faker.address().city();
    }

    public String getRandomState() {
        if (country.equals(countryArray[1])) {
            state = faker.address().state();
            stateAbbr = new StateAbbrHandler().getStates().get(state);
        }
        else state = null;
        System.out.println("STATE ABNR: " + stateAbbr);
        System.out.println("STATE : " + state);
        return state;
    }

    public String getCountry(int index) {
        country = countryArray[0];
        return country;
    }

    public String getRandomCountry() {
        country = countryArray[getRandomNumber(0, countryArray.length - 1)];
        return country;
    }

    public String getRandomZipCode() {
        if (state == null) {
            String zipCode = faker.address().zipCode().substring(0, 5);
            return zipCode.substring(0, 2) + "-" + zipCode.substring(2);
        } else {
            return faker.address().zipCodeByState(stateAbbr);
        }
    }

    public String getRandomShippingMethod() {
        return shippingMethodArray[getRandomNumber(0, shippingMethodArray.length - 1)];
    }

    public int parseInt(String value) {
        return Integer.parseInt(value.replaceAll("[^0-9]", ""));
    }

    public double parseDouble(String value) {
        return Double.parseDouble(value.replaceAll("[^0-9.]", ""));
    }

    public String getOnlyFirstLetterUpperCase(String text) {
        return text.substring(0, 1).toUpperCase(Locale.ROOT) + text.substring(1).toLowerCase(Locale.ROOT);
    }

    public String getEachFirstWordLetterUpperCase(String text) {
        return WordUtils.capitalize(text.toLowerCase(Locale.ROOT));
    }

    public void setValueWithKeyArrow(WebElement element, double currentValue, double valueToSet, boolean isHorizontalArrow) {
        Keys direction;
        Keys addDirection;
        Keys subDirection;
        if (isHorizontalArrow) {
            addDirection = Keys.ARROW_RIGHT;
            subDirection = Keys.ARROW_LEFT;
        } else {
            addDirection = Keys.ARROW_UP;
            subDirection = Keys.ARROW_DOWN;
        }
        double slideValue = valueToSet - currentValue;
        if (currentValue != valueToSet) {
            if (slideValue > 0) direction = addDirection;
            else direction = subDirection;
            for (int i = 0; i < Math.abs(slideValue); i++) {
                sendActionToElement(element, direction);
            }
        }
    }

    public String getCurrentDate(String pattern){
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        Date date = new Date();
        return formatter.format(date);
    }

    public double roundUpToSecondDecimalPlace(double number) {
        return (double) Math.round(number * 100) / 100;
    }

    public String substringToFirstOccurOfSign(String base, String sign){
        int end = base.indexOf(sign);
        return base.substring(0, end);
    }
}