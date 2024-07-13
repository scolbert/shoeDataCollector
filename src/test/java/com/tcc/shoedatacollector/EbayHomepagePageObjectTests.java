package com.tcc.shoedatacollector;

import com.tcc.shoedatacollector.pageobjects.EbayHomepagePageObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EbayHomepagePageObjectTests {
    @Test
    public void testOpenEbayHomePage_opensAPage_whenHappyDay() {
        try {
            WebdriverService.createDriver();
            EbayHomepagePageObject page = new EbayHomepagePageObject();
            page.openEbayHomepage();
            assertEquals(page.getUrl(), "https://www.ebay.com/");
        } finally {
            WebdriverService.closeDriver();
        }
    }
}
