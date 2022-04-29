package com.test.shop;

import com.configuration.TestBase;
import com.shop.pages.configuration.userConfiguration.ShippingFactory;
import com.shop.pages.configuration.userConfiguration.UserFactory;
import com.shop.steps.CheckoutStep;
import com.shop.steps.HeaderStep;
import com.shop.steps.RegistrationStep;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckoutTest extends TestBase {
    private static Logger log = LoggerFactory.getLogger(CheckoutTest.class);

    @Test
    public void shouldCheckEachCheckoutPlaceValues() {
        RegistrationStep registrationStep = new RegistrationStep(driver, new UserFactory(driver).getRandomUser());
        HeaderStep headerStep = new HeaderStep(driver, homePage);

        registrationStep.registryUser();

        headerStep
                .addItemsToBasket(4, homePage.getRandomNumber(1, 3), true)
                .addItemToBasket(homePage.getRandomNumber(1, 3), false);

        CheckoutStep checkoutStep = new CheckoutStep(driver, new ShippingFactory(driver).getRandomShippingPropertiesForPoland(), headerStep.getItemList());
        checkoutStep
                .fillShoppingProperty()
                .fillPaymentMethod()
                .checkOrderItems()
                .setOrder()
                .checkOrderProperties()
                .checkOrderDetails();

        checkoutStep.assertCheckoutTest();
        log.info(passed, passedMessage);
    }
}