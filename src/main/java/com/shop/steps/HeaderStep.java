package com.shop.steps;

import com.shop.pages.HeaderPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HeaderStep extends HeaderPage {

    public HeaderStep(WebDriver driver) {
        super(driver);
    }

    public void iterateAllCategories() {
        for (WebElement category : categoriesList) {
            hoverOnElement(category);
        }
        System.out.println("IS?: " + isElementVisible(subMenuList.get(0)));
    }
}
