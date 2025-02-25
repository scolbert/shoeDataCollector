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
    private static final String seller = "salty-solesfl";
    private static List<SearchResultsItem> searchResultsItems;

    // Normally I would avoid reusing the same page object in multiple tests to avoid one test influencing another.
    // In this case tests only read the page object so one test will not become dependent on another.
    // Since I cannot mock out eBay due to technical issues, this approach avoids spamming eBay.
    @BeforeAll
    static void setUp() {
        WebDriver sbsPage = WebdriverService.openSearchBySeller(seller);
        SearchBySellerPageTools sbsTools = new SearchBySellerPageTools();
        searchResultsItems = sbsTools.getListings(sbsPage);
    }

    @AfterAll
    static void tearDown() {
        WebdriverService.closeDriver();
    }

    @Test
    void testGetSearchResultItems_returnsAtLeastOneItem_whenHappyDay() {
        assertFalse(searchResultsItems.isEmpty());
    }
    
    @Test
    void testGetSearchResultItems_returnsValueInTitle_whenHappyDay() {
        assertNotNull(searchResultsItems.get(0).getTitle());
        assertFalse(searchResultsItems.get(0).getTitle().isEmpty());
    }

    //NOTE: At this point I cannot mock eBays site. This means that I cannot guaranty that the seller will always have
    // 240 items listed. If they have less than 240 items listed, this test will fail
    //TODO update this test to use eBay mock when I have one.
    @Test
    void testGetSearchResultItems_returnsPageOfItems_whenSellerHasMoreThanOnePageOfItems() {
        assert(searchResultsItems.size() == 240); // If this number is larger than 240 than it is likely that we have stopped filtering out the 'Shop on eBay' items
        assertNotNull(searchResultsItems.get(239).getTitle()); //last item in the list should be populated with data
        assertFalse(searchResultsItems.get(239).getTitle().isEmpty()); //last item in the list should be populated with data
    }

    @Test
    void testGetSearchResultItems_returnsNoItemsWithShopOnEbayTitle_whenSellerHasMoreThanOnePageOfItems() {
        for (SearchResultsItem item : searchResultsItems) {
            assertFalse(item.getTitle().contains("Shop on eBay"));
        }
    }

    // Because I do not have a mock of eBays site yet, this test may pass even if the feature is broken.
    // TODO Update this test to use eBay mock when I have one.
    @Test
    void testGetSearchResultItems_returnsNoItemsWithHtmlInTitle_whenSellerHasMoreThanOnePageOfItems() {
        for (SearchResultsItem item : searchResultsItems) {
            assertFalse(item.getTitle().contains("<"));
        }
    }

    // Because I do not have a mock of eBays site yet, this test may pass even if the feature is broken.
    // TODO Update this test to use eBay mock when I have one.
    @Test
    void testGetSearchResultItems_returnsNoItemsWithNewListingInTitle_whenSellerHasMoreThanOnePageOfItems() {
        for (SearchResultsItem item : searchResultsItems) {
            assertFalse(item.getTitle().startsWith("New Listing"));
        }
    }

    @Disabled  // comment this out if I need to see all titles. This is for development purposes only.
    @Test
    void displayAllItemTitles(){
        for (SearchResultsItem item : searchResultsItems) {
            System.out.println(item.getTitle());
        }
    }

    @Disabled // enable when I need to see the actual web page that is being used in tests. This is for development purposes only.
    @Test
    void openWebPageInChromeBrowser() {
        WebdriverService.OpenSearchBySellerInBrowser(seller);
    }
}
