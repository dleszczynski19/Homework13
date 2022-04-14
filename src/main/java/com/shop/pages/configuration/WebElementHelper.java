package com.shop.pages.configuration;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class WebElementHelper extends BasePage {
    private static Logger log = LoggerFactory.getLogger(WebElementHelper.class);
    private Actions actions;

    public WebElementHelper(WebDriver driver) {
        super(driver);
        actions = new Actions(driver);
    }

    public void click(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
        } catch (StaleElementReferenceException sere) {
            // simply retry finding the element in the refreshed DOM
            element.click();
        } catch (TimeoutException toe) {
            log.error("Element " + element.toString() + " was not clickable after 10 seconds");
        }
    }

    public void sendKeys(WebElement element, String value) {
        waitForElement(element);
        element.sendKeys(value);
    }

    public void waitForElement(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException toe) {
            log.error("Element " + element.toString() + " was not visible after 10 seconds");
            assert false;
        }
    }

    public boolean isElementVisible(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void scrollToElement(WebElement element) {
        while (!isElementVisible(element)) {
            js.executeScript("window.scrollBy(0,250)", "");
        }
        log.info("Scrolled to element: " + element);
    }

    public String getElementNameFromList(List<WebElement> list, int index) {
        waitForElement(list.get(0));
        return list.get(index).getText();
    }

    public String getElementNameFromList(List<WebElement> list, String elementName) {
        waitForElement(list.get(0));
        return findElementByName(list, elementName).getText();
    }

    public WebElement findElementByName(List<WebElement> elementList, String element) {
        for (WebElement webElement : elementList) {
            System.out.println("el: " + webElement.getText());
        }
        return elementList.stream()
                .filter(el -> el.getText().equals(element))
                .reduce((f, s) -> s)
                .orElseThrow(() -> new RuntimeException("Can't find option"));
    }

    public boolean isPageSwitched(WebElement element) {
        if (isElementVisible(element)) {
            log.info("The Page has been switched");
            return true;
        } else {
            log.error("The page has not been switched");
            return false;
        }
    }

    public void hoverOnElement(WebElement element) {
        waitForElement(element);
        actions.moveToElement(element).perform();
        log.info("Hover over element: " + element.toString());
    }

    public void dragAndDrop(WebElement elementDrag, WebElement elementDrop) {
        actions.dragAndDrop(elementDrag, elementDrop)
                .perform();
        log.info("Element " + elementDrag.toString() + " dropped to " + elementDrop.toString());
    }

    public Dimension getElementSize(WebElement element) {
        int height = Integer.parseInt(element.getCssValue("height")
                .replace("px", ""));
        int width = Integer.parseInt(element.getCssValue("width")
                .replace("px", ""));
        return new Dimension(width, height);
    }
}