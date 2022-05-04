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
        HeaderStep headerStep = new HeaderStep(driver, homePage);

        log.info("Start checking each place values for checkout test");
        new RegistrationStep(driver, new UserFactory(driver).getRandomUser()).registryUser();

        headerStep
                .addItemsToBasket(homePage.parseInt(System.getProperty("checkoutTestItemCount")), homePage.getRandomNumber(
                        homePage.parseInt(System.getProperty("checkoutTestMinQuantity")),
                        homePage.parseInt(System.getProperty("checkoutTestMaxQuantity"))), true)
                .addItemToBasket(homePage.getRandomNumber(
                        homePage.parseInt(System.getProperty("checkoutTestMinQuantity")),
                        homePage.parseInt(System.getProperty("checkoutTestMaxQuantity"))), false);

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