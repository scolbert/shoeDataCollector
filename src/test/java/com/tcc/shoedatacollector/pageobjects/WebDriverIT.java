package com.tcc.shoedatacollector.pageobjects;

import com.tcc.shoedatacollector.WebdriverService;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class WebDriverIT {
    //test to show that the WebDriver is working using junit
    @Test
    public void testWebDriverCreateAndGet_createsAndGetsAWebpage_whenHappyDay() {
            try {
                WebDriver driver = WebdriverService.createDriver();

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
                WebDriver driver = WebdriverService.createDriver();
                driver.get("https://www.google.com");

                WebdriverService.closeDriver();

                assertNull(WebdriverService.getHeadlessDriver());
            } finally {
                WebdriverService.closeDriver();
            }
    }
}
