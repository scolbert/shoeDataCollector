package com.tcc.shoedatacollector;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class WebDriverTests {
    //test to show that the WebDriver is working using junit
    @Test
    public void testWebDriverCreateAndGet_createsAndGetsAWebpage_whenHappyDay() {
            try {
                WebdriverService.createDriver();

                WebDriver driver = WebdriverService.getDriver();
                driver.get("https://www.google.com");
                String currentUrl = driver.getCurrentUrl();

                assertEquals("https://www.google.com/", currentUrl);
            } finally {
                WebdriverService.closeDriver();
            }
    }

    @Test
    public void testCloseWebDriver_closesAWebpage_whenHappyDay() {
            try {
                WebdriverService.createDriver();
                WebDriver driver = WebdriverService.getDriver();
                driver.get("https://www.google.com");

                WebdriverService.closeDriver();

                assertNull(WebdriverService.getDriver());
            } finally {
                WebdriverService.closeDriver();
            }
    }
}
