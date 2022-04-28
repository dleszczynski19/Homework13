package com.shop.steps;

import com.shop.models.User;
import com.shop.pages.HeaderPage;
import com.shop.pages.LoginPage;
import com.shop.pages.RegistrationPage;
import com.shop.pages.configuration.userConfiguration.UserFactory;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegistrationStep extends HeaderPage {
    private Logger log = LoggerFactory.getLogger(RegistrationStep.class);
    private User user;

    public RegistrationStep(WebDriver driver, User user) {
        super(driver);
        this.user = user;
    }

    public RegistrationStep registryUser() {
        LoginPage loginPage = new LoginPage(driver);
        UserFactory userFactory = new UserFactory(driver);

        goToSignIn();
        loginPage
                .goToRegistration()
                .chooseGenderTitle(RegistrationPage.GenderTitle.valueOf(user.getSocialTitle()))
                .fillFirstName(user.getFirstName())
                .fillLastName(user.getLastName())
                .fillEmail(user.getEmail())
                .fillBirthday(user.getDay(), user.getMonth(), user.getYear())
                .fillPassword(user.getPassword())
                .acceptOffers(user.isReceiveOffers())
                .acceptPrivacy(user.isDataPrivacy())
                .acceptNewsletter(user.isSingUpNewsletter())
                .acceptStatute(user.isAcceptPolicy())
                .clickOnSaveButton();
        softAssert.assertThat(getUserAccountName()).isEqualTo(user.getFirstName() + " " + user.getLastName());
        softAssert.assertAll();
        userFactory.getFileHandler().addUserToFile(user);
        log.info("User registered");
        return this;
    }
}
