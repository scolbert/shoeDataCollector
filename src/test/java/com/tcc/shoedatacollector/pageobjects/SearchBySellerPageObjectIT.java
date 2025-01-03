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

    //NOTE: that this test depends on Saltys Soles having at least 240 items listed. If they have less than 240 items listed, this test will fail
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

    // TODO can I test the following things at this level? Perhaps just tests to confirm that the proper methods are being called
        // TODO remove 'new items' tag from first of titles
        // TODO handle this type of title: "<span class="LIGHT_HIGHLIGHT">New Listing</span>OXO 8-Piece Refrigerator Organization Set 13347200 Good Grips GG NEW"
        // TODO add tests to make sure that the 'Shop on eBay' items are being filtered out



}
