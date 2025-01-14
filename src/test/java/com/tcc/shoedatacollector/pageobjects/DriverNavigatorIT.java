package com.tcc.shoedatacollector.pageobjects;

import com.tcc.shoedatacollector.WebdriverService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DriverNavigatorIT {
    @Test
    public void testOpenEbayHomePage_opensAPage_whenHappyDay() {
        try {
            WebdriverService.createDriver();
            DriverNavigator.openEbayHomepage();
            assertEquals(DriverNavigator.getUrl(), "https://www.ebay.com/");
        } finally {
            WebdriverService.closeDriver();
        }
    }

    @Test
    public void testOpenAdvancedSearchPage_opensAPage_whenHappyDay() {
        try {
            WebdriverService.createDriver();
            DriverNavigator.openAdvancedSearchPage();
            assertEquals(DriverNavigator.getUrl(), "https://www.ebay.com/sch/ebayadvsearch");
        } finally {
            WebdriverService.closeDriver();
        }
    }

    @Test
    public void testSearchBySeller_opensSellersListings_whenHappyDay() {
        try {
            WebdriverService.createDriver();
            DriverNavigator.openSearchBySeller("salty-solesfl");
            System.out.println(DriverNavigator.getUrl());
            assertEquals( "https://www.ebay.com/sch/i.html?_ssn=salty-solesfl&_ipg=240", DriverNavigator.getUrl());
        } finally {
             WebdriverService.closeDriver();
        }
    }
}
