package com.tcc.shoedatacollector.pageobjects;

import com.tcc.shoedatacollector.DTOs.SearchResultsItem;
import com.tcc.shoedatacollector.WebdriverService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

public class SearchBySellerPageObjectTest {
    private WebDriver driver;
    @BeforeEach
    public void setUp() {
        WebdriverService.createDriver();
        driver = WebdriverService.getDriver();
        //TODO clean up syntax for adding navigation. Should be able to use static method to avoid creating an instance of the class. Possibly combine create driver with navigation configurer into one config class
        DriverNavigationConfigurer page = new DriverNavigationConfigurer();
        page.searchBySeller("salty-solesfl");
    }

    @AfterEach
    public void tearDown() {
        WebdriverService.closeDriver();
    }

    @Test
    public void testGetSearchResultItems_returnsAtLeastOneItem_whenHappyDay() {
        SearchBySellerPageObject pageObject = new SearchBySellerPageObject();
        SearchResultsItem[] searchResultsItems = pageObject.getSearchResultItems(driver);
        assert(searchResultsItems.length > 0);
    }
    
//    @Test
//    public void
}
