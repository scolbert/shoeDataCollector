package com.tcc.shoedatacollector.pageobjects;

import com.tcc.shoedatacollector.WebdriverService;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class WebDriverServiceIT {
    @Test
    void testWebDriverCreateAndGet_createsAndGetsAWebpage_whenHappyDay() {
            try {
                WebDriver driver = WebdriverService.createHeadlessDriver();

                driver.get("https://www.google.com");
                String currentUrl = driver.getCurrentUrl();

                assertEquals("https://www.google.com/", currentUrl);
            } finally {
                WebdriverService.closeDriver();
            }
    }

    @Test
    void testCloseWebDriver_closesAWebpage_whenHappyDay() {
            try {
                WebDriver driver = WebdriverService.createHeadlessDriver();
                driver.get("https://www.google.com");
                WebdriverService.closeDriver();

                assertNull(WebdriverService.getHeadlessDriver());
            } finally {
                WebdriverService.closeDriver();
            }
    }

    @Test
    void testOpenEbayHomePage_opensAPage_whenHappyDay() {
        try {
            WebdriverService.openEbayHomepage();
            assertEquals(WebdriverService.getUrl(), "https://www.ebay.com/");
        } finally {
            WebdriverService.closeDriver();
        }
    }

    @Test
    void testOpenAdvancedSearchPage_opensAPage_whenHappyDay() {
        try {
            WebdriverService.openAdvancedSearchPage();
            assertEquals(WebdriverService.getUrl(), "https://www.ebay.com/sch/ebayadvsearch");
        } finally {
            WebdriverService.closeDriver();
        }
    }

    @Test
    void testSearchBySeller_opensSellersListings_whenHappyDay() {
        try {
            WebdriverService.openSearchBySeller("salty-solesfl");
            assertEquals( "https://www.ebay.com/sch/i.html?_ssn=salty-solesfl&_ipg=240", WebdriverService.getUrl());
        } finally {
            WebdriverService.closeDriver();
        }
    }
}
