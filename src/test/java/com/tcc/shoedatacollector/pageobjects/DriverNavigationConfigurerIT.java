package com.tcc.shoedatacollector.pageobjects;

import com.tcc.shoedatacollector.WebdriverService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DriverNavigationConfigurerIT {
    @Test
    public void testOpenEbayHomePage_opensAPage_whenHappyDay() {
        try {
            WebdriverService.createDriver();
            DriverNavigationConfigurer page = new DriverNavigationConfigurer();
            page.openEbayHomepage();
            assertEquals(page.getUrl(), "https://www.ebay.com/");
        } finally {
            WebdriverService.closeDriver();
        }
    }

    @Test
    public void testOpenAdvancedSearchPage_opensAPage_whenHappyDay() {
        try {
            WebdriverService.createDriver();
            DriverNavigationConfigurer page = new DriverNavigationConfigurer();
            page.openEbayHomepage();
            page.openAdvancedSearchPage();
            assertEquals(page.getUrl(), "https://www.ebay.com/sch/ebayadvsearch");
        } finally {
            WebdriverService.closeDriver();
        }
    }

    @Test
    public void testSearchBySeller_opensSellersListings_whenHappyDay() {
        try {
            WebdriverService.createDriver();
            DriverNavigationConfigurer page = new DriverNavigationConfigurer();
            page.openEbayHomepage();
            page.searchBySeller("salty-solesfl");
            System.out.println(page.getUrl());
            assertEquals( "https://www.ebay.com/sch/i.html?_ssn=salty-solesfl&_ipg=240", page.getUrl());
        } finally {
             WebdriverService.closeDriver();
        }
    }
}
