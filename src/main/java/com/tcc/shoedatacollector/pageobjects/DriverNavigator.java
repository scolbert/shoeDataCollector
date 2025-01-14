package com.tcc.shoedatacollector.pageobjects;

import com.tcc.shoedatacollector.WebdriverService;

public class DriverNavigator {
    public static void openEbayHomepage() {
        WebdriverService.getHeadlessDriver().get("https://www.ebay.com");
    }

    public static String getUrl() {
        return WebdriverService.getHeadlessDriver().getCurrentUrl();
    }

    public static void openAdvancedSearchPage() {
        WebdriverService.getHeadlessDriver().get("https://www.ebay.com/sch/ebayadvsearch");
    }
    public static void openSearchBySeller(String seller) {
        WebdriverService.getHeadlessDriver().get("https://www.ebay.com/sch/i.html?_ssn=" + seller + "&_ipg=240");
    }

    public static void OpenSearchBySellerInBrowser(String seller) {
        WebdriverService.driverWithHead.get("https://www.ebay.com/sch/i.html?_ssn=" + seller + "&_ipg=240");
    }
}
