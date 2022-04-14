package com.shop.pages.configuration;

import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class BasePage {
    private static Logger log = LoggerFactory.getLogger(BasePage.class);
    public WebDriver driver;
    public JavascriptExecutor js;
    public WebDriverWait wait;

    public BasePage() {
    }

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        js = (JavascriptExecutor) driver;
    }

    public void doScreenShot(String fileName) {
        TakesScreenshot screenshot = ((TakesScreenshot) driver);
        File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
        File destFile = new File("src/main/resources/files/" + fileName + ".png");
        try {
            FileUtils.copyFile(srcFile, destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("Screen shot taken");
    }

    public WebElement clickRandomElement(List<WebElement> list) {
        return list.get(new Random().nextInt(list.size()));
    }

    public void selectRandomElement(Select selectList) {
        selectList.selectByIndex(new Random().nextInt(selectList.getOptions().size()));
    }

    public Dimension getWindowSize() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        int width = Integer.parseInt(String.valueOf(jse.executeScript("return window.innerWidth")));
        int height = Integer.parseInt(String.valueOf(jse.executeScript("return window.innerHeight")));
        return new Dimension(width, height);
    }
}