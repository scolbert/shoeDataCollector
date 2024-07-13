package com.tcc.shoedatacollector.pageobjects;

import com.tcc.shoedatacollector.WebdriverService;

public class EbayHomepagePageObject {
    public void openEbayHomepage() {
        WebdriverService.getDriver().get("https://www.ebay.com");
    }

    public String getUrl() {
        return WebdriverService.getDriver().getCurrentUrl();
    }
}
