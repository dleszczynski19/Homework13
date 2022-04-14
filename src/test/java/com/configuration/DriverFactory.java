package com.configuration;

import com.shop.models.yaml.Browser;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

public class DriverFactory {
    private static Logger log = LoggerFactory.getLogger(DriverFactory.class);
    private final Browsers browserName;
    private final boolean headlessBrowser;
    private final int webElementTimeOut;
    private WebDriver driver;
    private Browser browser;

    public DriverFactory(Browser browser) {
        this.browser = browser;
        this.headlessBrowser = browser.isHeadlessBrowser();
        this.webElementTimeOut = browser.getWebElementTimeOut();
        this.browserName = Browsers.valueOf(browser.getBrowserName().toUpperCase(Locale.ROOT));
        log.info("Browser name: " + browserName);
        log.info("WebElement TimeOut: " + webElementTimeOut);
        log.info("Is headless browser: " + headlessBrowser);
        log.info("-------------------------------------");
    }

    public WebDriver getDriver() {
        switch (this.browserName) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(new ChromeOptions().addArguments("start-maximized"));
                break;
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver(new FirefoxOptions().addArguments("start-maximized"));
                break;
            case IE:
                WebDriverManager.iedriver().setup();
                driver = new InternetExplorerDriver();
                break;
            case EDGE:
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
        }
        driver.get(browser.getAppUrl());
        return this.driver;
    }
}