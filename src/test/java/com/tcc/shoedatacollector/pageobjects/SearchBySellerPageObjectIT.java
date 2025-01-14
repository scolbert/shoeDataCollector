package com.tcc.shoedatacollector.pageobjects;

import com.tcc.shoedatacollector.DTOs.SearchResultsItem;
import com.tcc.shoedatacollector.WebdriverService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SearchBySellerPageObjectIT {
    private WebDriver driver;
    @BeforeEach
    public void setUp() {
        driver = WebdriverService.createDriver();
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
        List<SearchResultsItem> searchResultsItems = pageObject.getSearchResultItems(driver);
        assertFalse(searchResultsItems.isEmpty());
    }
    
    @Test
    public void testGetSearchResultItems_returnsValueInTitle_whenHappyDay() {
        SearchBySellerPageObject pageObject = new SearchBySellerPageObject();
        List<SearchResultsItem> searchResultsItems = pageObject.getSearchResultItems(driver);
        assertNotNull(searchResultsItems.get(0).getTitle());
        assertFalse(searchResultsItems.get(0).getTitle().isEmpty());
    }

    //NOTE: that this test depends on The scanned seller having at least 240 items listed. If they have less than 240 items listed, this test will fail
    //TODO find a way to test this without relying on the source page
    @Test
    public void testGetSearchResultItems_returnsPageOfItems_whenSellerHasMoreThanOnePageOfItems() {
        SearchBySellerPageObject pageObject = new SearchBySellerPageObject();
        List<SearchResultsItem> searchResultsItems = pageObject.getSearchResultItems(driver);
        assert(searchResultsItems.size() == 240); // If this number is larger than 240 than it is likely that we have stopped filtering out the 'Shop on eBay' items
        assertNotNull(searchResultsItems.get(239).getTitle()); //last item in the list should be populated with data
        assertFalse(searchResultsItems.get(239).getTitle().isEmpty()); //last item in the list should be populated with data
    }

    @Test
    public void displayAllItemTitles(){
        SearchBySellerPageObject pageObject = new SearchBySellerPageObject();
        List<SearchResultsItem> searchResultsItems = pageObject.getSearchResultItems(driver);
        for (SearchResultsItem item : searchResultsItems) {
            System.out.println(item.getTitle());
        }
    }

    @Test
    public void testGetSearchResultItems_returnsNoItemsWithShopOnEbayTitle_whenSellerHasMoreThanOnePageOfItems() {
        SearchBySellerPageObject pageObject = new SearchBySellerPageObject();
        List<SearchResultsItem> searchResultsItems = pageObject.getSearchResultItems(driver);
        for (SearchResultsItem item : searchResultsItems) {
            assertFalse(item.getTitle().contains("Shop on eBay"));
        }
    }

    //TODO these 2 tests may pass even if they are broken because the source page may not have instances of these issues
    // Need to find a way to test these without relying on the source page
    @Test
    public void testGetSearchResultItems_returnsNoItemsWithHtmlInTitle_whenSellerHasMoreThanOnePageOfItems() {
        SearchBySellerPageObject pageObject = new SearchBySellerPageObject();
        List<SearchResultsItem> searchResultsItems = pageObject.getSearchResultItems(driver);
        for (SearchResultsItem item : searchResultsItems) {
            assertFalse(item.getTitle().contains("<"));
        }
    }

    @Test
    public void testGetSearchResultItems_returnsNoItemsWithNewListingInTitle_whenSellerHasMoreThanOnePageOfItems() {
        SearchBySellerPageObject pageObject = new SearchBySellerPageObject();
        List<SearchResultsItem> searchResultsItems = pageObject.getSearchResultItems(driver);
        for (SearchResultsItem item : searchResultsItems) {
            assertFalse(item.getTitle().startsWith("New Listing"));
        }
    }
}
