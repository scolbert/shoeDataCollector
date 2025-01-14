package com.tcc.shoedatacollector.pageobjects;

import com.tcc.shoedatacollector.WebdriverService;

public class DriverNavigator {
    public static void openEbayHomepage() {
        WebdriverService.getDriver().get("https://www.ebay.com");
    }

    public static String getUrl() {
        return WebdriverService.getDriver().getCurrentUrl();
    }

    public static void openAdvancedSearchPage() {
        WebdriverService.getDriver().get("https://www.ebay.com/sch/ebayadvsearch");
    }
    public static void openSearchBySeller(String seller) {
        WebdriverService.getDriver().get("https://www.ebay.com/sch/i.html?_ssn=" + seller + "&_ipg=240");
    }
}
