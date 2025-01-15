package com.tcc.shoedatacollector.pageobjects;

import com.tcc.shoedatacollector.DTOs.SearchResultsItem;
import com.tcc.shoedatacollector.WebdriverService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SearchBySellerPageToolsIT {
    private static WebDriver sbsPage;
    private static final String seller = "salty-solesfl";

    @BeforeAll

    public static void setUp() {
        sbsPage = WebdriverService.openSearchBySeller(seller);
    }

    @AfterAll
    public static void tearDown() {
        WebdriverService.closeDriver();
    }

    @Test
    public void testGetSearchResultItems_returnsAtLeastOneItem_whenHappyDay() {
        SearchBySellerPageTools sbsTools = new SearchBySellerPageTools();
        List<SearchResultsItem> searchResultsItems = sbsTools.getListings(sbsPage);
        assertFalse(searchResultsItems.isEmpty());
    }
    
    @Test
    public void testGetSearchResultItems_returnsValueInTitle_whenHappyDay() {
        SearchBySellerPageTools sbsTools = new SearchBySellerPageTools();
        List<SearchResultsItem> searchResultsItems = sbsTools.getListings(sbsPage);
        assertNotNull(searchResultsItems.get(0).getTitle());
        assertFalse(searchResultsItems.get(0).getTitle().isEmpty());
    }

    //NOTE: that this test depends on The scanned seller having at least 240 items listed. If they have less than 240 items listed, this test will fail
    //TODO find a way to test this without relying on the source page
    @Test
    public void testGetSearchResultItems_returnsPageOfItems_whenSellerHasMoreThanOnePageOfItems() {
        SearchBySellerPageTools sbsTools = new SearchBySellerPageTools();
        List<SearchResultsItem> searchResultsItems = sbsTools.getListings(sbsPage);
        assert(searchResultsItems.size() == 240); // If this number is larger than 240 than it is likely that we have stopped filtering out the 'Shop on eBay' items
        assertNotNull(searchResultsItems.get(239).getTitle()); //last item in the list should be populated with data
        assertFalse(searchResultsItems.get(239).getTitle().isEmpty()); //last item in the list should be populated with data
    }

    @Test
    public void testGetSearchResultItems_returnsNoItemsWithShopOnEbayTitle_whenSellerHasMoreThanOnePageOfItems() {
        SearchBySellerPageTools sbsTools = new SearchBySellerPageTools();
        List<SearchResultsItem> searchResultsItems = sbsTools.getListings(sbsPage);
        for (SearchResultsItem item : searchResultsItems) {
            assertFalse(item.getTitle().contains("Shop on eBay"));
        }
    }

    //TODO these 2 tests may pass even if they are broken because the source page may not have instances of these issues
    // Need to find a way to test these without relying on the source page
    @Test
    public void testGetSearchResultItems_returnsNoItemsWithHtmlInTitle_whenSellerHasMoreThanOnePageOfItems() {
        SearchBySellerPageTools sbsTools = new SearchBySellerPageTools();
        List<SearchResultsItem> searchResultsItems = sbsTools.getListings(sbsPage);
        for (SearchResultsItem item : searchResultsItems) {
            assertFalse(item.getTitle().contains("<"));
        }
    }

    @Test
    public void testGetSearchResultItems_returnsNoItemsWithNewListingInTitle_whenSellerHasMoreThanOnePageOfItems() {
        SearchBySellerPageTools sbsTools = new SearchBySellerPageTools();
        List<SearchResultsItem> searchResultsItems = sbsTools.getListings(sbsPage);
        for (SearchResultsItem item : searchResultsItems) {
            assertFalse(item.getTitle().startsWith("New Listing"));
        }
    }

    @Disabled  // comment this out if I need to see all titles. This is for development purposes only.
    @Test
    public void displayAllItemTitles(){
        SearchBySellerPageTools sbsTools = new SearchBySellerPageTools();
        List<SearchResultsItem> searchResultsItems = sbsTools.getListings(sbsPage);
        for (SearchResultsItem item : searchResultsItems) {
            System.out.println(item.getTitle());
        }
    }

    @Disabled // enable when I need to see the actual web page that is being used in tests. This is for development purposes only.
    @Test
    public void openWebPageInChromeBrowser() {
        WebdriverService.OpenSearchBySellerInBrowser(seller);
    }

    //TODO : remove public keyword from all tests
}
