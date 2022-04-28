package com.shop.pages.configuration.userConfiguration;

import com.shop.models.Shipping;
import com.shop.pages.handlers.DataHandler;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShippingFactory extends DataHandler {
    private static Logger log = LoggerFactory.getLogger(UserFactory.class);

    public ShippingFactory(WebDriver driver) {
        super(driver);
    }

    public Shipping getRandomShippingProperties(){
        return new Shipping.ShippingBuilder()
                .address(getRandomAddress())
                .country(getRandomCountry())
                .city(getRandomCity())
                .state(getRandomState())
                .zipCode(getRandomZipCode())
                .build();
    }

    public Shipping getRandomShippingPropertiesForPoland(){
        return new Shipping.ShippingBuilder()
                .address(getRandomAddress())
                .country(getCountry(0))
                .city(getRandomCity())
                .state(getRandomState())
                .zipCode(getRandomZipCode())
                .build();
    }
}
