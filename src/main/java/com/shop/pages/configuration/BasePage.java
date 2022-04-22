package com.shop.pages.configuration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.server.handler.WebElementHandler;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class BasePage {
    private static Logger log = LoggerFactory.getLogger(BasePage.class);
    protected WebDriver driver;
    protected JavascriptExecutor js;
    protected WebDriverWait wait;
    protected static PagesFactory pageFactory;

    public BasePage() {
    }

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        js = (JavascriptExecutor) driver;
        pageFactory = new PagesFactory(driver);
    }

    @FindBy(css = "#wrapper ol")
    private WebElement path;

    public BasePage doScreenShot(String fileName) {
        TakesScreenshot screenshot = ((TakesScreenshot) driver);
        File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
        File destFile = new File("src/main/resources/files/" + fileName + ".png");
        try {
            FileUtils.copyFile(srcFile, destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("Screen shot taken");
        return this;
    }

    public Dimension getWindowSize() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        int width = Integer.parseInt(String.valueOf(jse.executeScript("return window.innerWidth")));
        int height = Integer.parseInt(String.valueOf(jse.executeScript("return window.innerHeight")));
        return new Dimension(width, height);
    }

    public WebElement getPath() {
        return path;
    }
}