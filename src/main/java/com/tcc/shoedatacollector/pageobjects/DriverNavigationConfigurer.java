package com.tcc.shoedatacollector.pageobjects;

import com.tcc.shoedatacollector.WebdriverService;

public class DriverNavigationConfigurer {
    public void openEbayHomepage() {
        WebdriverService.getDriver().get("https://www.ebay.com");
    }

    public String getUrl() {
        return WebdriverService.getDriver().getCurrentUrl();
    }

    public void openAdvancedSearchPage() {
        WebdriverService.getDriver().get("https://www.ebay.com/sch/ebayadvsearch");
    }
    public void searchBySeller(String seller) {
        WebdriverService.getDriver().get("https://www.ebay.com/sch/i.html?_ssn=" + seller + "&_ipg=240");
    }
}
