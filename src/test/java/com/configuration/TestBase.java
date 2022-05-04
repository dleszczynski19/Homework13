package com.configuration;

import com.shop.pages.HomePage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class TestBase {
    public WebDriver driver;
    public final static String passedMessage = "Test Passed!";
    public final static Marker passed = MarkerFactory.getMarker("PASSED");
    private static Logger log = LoggerFactory.getLogger(TestBase.class);
    private static DriverFactory driverFactory;
    private static EnvironmentConfig environmentConfig;
    public static HomePage homePage;

    @BeforeAll
    static void setup() {
        environmentConfig = EnvironmentConfig.getInstance();
        driverFactory = environmentConfig.getBrowserEnvironment();
        log.info("Environment setup properly");
    }

    @BeforeEach
    void initializeDriver() {
        driver = driverFactory.getDriver();
        log.info("Driver initialized properly");
        homePage = new HomePage(driver);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        log.info("Driver closed properly");
    }
}