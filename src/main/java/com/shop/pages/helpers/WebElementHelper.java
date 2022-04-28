package com.shop.pages.helpers;

import com.shop.pages.configuration.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class WebElementHelper extends BasePage {
    private static Logger log = LoggerFactory.getLogger(WebElementHelper.class);
    private Actions actions;

    public WebElementHelper(WebDriver driver) {
        super(driver);
        actions = new Actions(driver);
    }

    public void clickOnElement(WebElement element) {
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

    public void sendKeysToElement(WebElement element, String value) {
        waitForElement(element);
        element.clear();
        element.sendKeys(value);
    }

    public void addKeysToElement(WebElement element, String value) {
        waitForElement(element);
        element.sendKeys(value);
    }

    public void sendActionToElement(WebElement element, Keys keys) {
        waitForElement(element);
        element.sendKeys(keys);
    }

    public void waitForElement(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException toe) {
            log.error("Element " + element.toString() + " was not visible after 10 seconds");
            assert false;
        }
    }

    public void waitForElementNotVisible(WebElement element) {
        try {
            wait.until(ExpectedConditions.invisibilityOf(element));
        } catch (TimeoutException toe) {
            log.error("Element " + element.toString() + " was visible after 10 seconds");
            assert false;
        }
    }

    public void waitForListSizeIsHigherThanZero(List<WebElement> webElementList) {
        int limiter = 0;
        int size = webElementList.size();
        while (size == 0 && limiter < 10) {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
            size = webElementList.size();
            limiter++;
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
        int limiter = 0;
        while (!isElementVisible(element) && limiter < 10) {
            js.executeScript("window.scrollBy(0,250)", "");
            limiter++;
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
        return elementList.stream()
                .filter(el -> el.getText().equals(element))
                .reduce((f, s) -> s)
                .orElseThrow(() -> new RuntimeException("Can't find option"));
    }

    public WebElement findElementByCss(String selector){
        return driver.findElement(By.cssSelector(selector));
    }

    public WebElement findElementByContainName(List<WebElement> elementList, String element) {
        return elementList.stream()
                .filter(el -> el.getText().contains(element))
                .reduce((f, s) -> s)
                .orElseThrow(() -> new RuntimeException("Can't find option"));
    }

    public WebElement findElementByElementInside(List<WebElement> elementList, String childrenSelector, String attribute, String value) {
        return   elementList.stream().filter(el -> el.findElement(By.cssSelector(childrenSelector)).getAttribute(attribute).equals(value))
                .reduce((f,s) -> s)
                .orElseThrow(() -> new RuntimeException("Can't find option"));

    }

    public Select getSelect(WebElement element) {
        return new Select(element);
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

    public void hoverElement(WebElement element) {
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

    public void waitForSite(String siteName) {
        int limiter = 0;
        while (!driver.getTitle().equals(siteName) && limiter < 10) {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
            limiter++;
        }
    }

    public boolean isCorrectPageLoaded(String pagePath) {
        waitForElement(getPath());
        if (getPath().getText().equals(pagePath)) {
            log.info("Page: " + pagePath + " is properly loaded");
            return true;
        } else {
            log.error("Page: " + pagePath + " is not properly loaded");
            return false;
        }
    }

    public boolean isAjaxCompletedTasks(){
        String state = js.executeScript("return jQuery.active").toString();
        return state.equals("0");
    }

    public boolean isPageProperlyLoaded() {
        return isDOMLoaded() && isAjaxCompletedTasks();
    }

    public boolean isDOMLoaded() {
        String state = js.executeScript("return document.readyState").toString();
        return state.equals("complete");
    }

    public void waitForPageLoaded(){
        wait.until(driver1 -> isPageProperlyLoaded());
    }

    public boolean isProductLoaded(String productName) {
        waitForElement(getPath());
        return getPath().getText().contains(productName);
    }

    public WebElement findElementInAnotherElement(WebElement mainElement, String elementToFind) {
        return mainElement.findElement(By.cssSelector(elementToFind));
    }

    public List<WebElement> findElementsInAnotherElement(WebElement mainElement, String elementToFind) {
        return mainElement.findElements(By.cssSelector(elementToFind));
    }

    public BasePage clickRandomElement(List<WebElement> list) {
        list.get(new Random().nextInt(list.size())).click();
        return this;
    }

    public String getElementText(WebElement element) {
        return element.getText();
    }

    public String getElementValue(WebElement element) {
        return element.getAttribute("value");
    }

    public WebElement getRandomElement(List<WebElement> list) {
        return list.get(new Random().nextInt(list.size()));
    }

    public BasePage selectRandomElement(Select selectList) {
        selectList.selectByIndex(new Random().nextInt(selectList.getOptions().size()));
        return this;
    }
}