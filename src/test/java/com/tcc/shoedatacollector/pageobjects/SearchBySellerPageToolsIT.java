package com.tcc.shoedatacollector.pageobjects;

import com.tcc.shoedatacollector.DTOs.SearchResultsItem;
import com.tcc.shoedatacollector.WebdriverService;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    void testGetSearchResultItems_returnsPageOfItemsWithRequiredFields_whenSellerHasMoreThanOnePageOfItems() {
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

    @Test
    void testGetSearchResultItems_returnsValueInCondition_whenHappyDay() {
        assertNotNull(searchResultsItems.get(0).getCondition(), "Condition is null for " + searchResultsItems.get(0).getTitle());
        assertFalse(searchResultsItems.get(0).getCondition().isEmpty(), "Condition is empty for " + searchResultsItems.get(0).getTitle());
    }

    @Test
    void testGetSearchResultItems_returnsValidValuesInCondition_whenHappyDay() {
        for (SearchResultsItem item : searchResultsItems) {
            assertTrue(isValidCondition(item.getCondition()), "Invalid condition for " + item.getTitle() + "Condition: " + item.getCondition());
        }
    }

    // Again, note that I cannot mock eBay so I cannot guaranty that the seller will always have 3 valid conditions.
    // TODO update this test to use eBay mock when I have one.
    @Test
    void testGetSearchResultItems_returnsEachOfTheThreeValidConditions_whenHappyDay() {
        boolean preOwned = false;
        boolean brandNew = false;
        boolean newOther = false;
        for (SearchResultsItem item : searchResultsItems) {
            if (item.getCondition().equals("Pre-Owned")) {
                preOwned = true;
            } else if (item.getCondition().equals("Brand New")) {
                brandNew = true;
            } else if (item.getCondition().equals("New (Other)")) {
                newOther = true;
            }
        }
        assertTrue(preOwned, "Pre-Owned condition not found on page");
        assertTrue(brandNew, "Brand New condition not found on page");
        assertTrue(newOther, "New (Other) condition not found on page");
    }

    @Test
    void testGetSearchResultItems_returnsValuesInPrice_whenHappyDay() {
        for (SearchResultsItem item : searchResultsItems) {
            assertHasPriceValues(item);
        }
    }

    @Test
    void testGetShippingPrice_returnsValue_whenHappyDay() {
        for (SearchResultsItem item : searchResultsItems) {
            assertNotNull(item.getShippingPrice(), "Shipping price is null for " + item.getTitle());
            assertTrue(item.getShippingPrice() >= 0, "Shipping price is negative for " + item.getTitle());
        }
    }

    private static void assertHasPriceValues(SearchResultsItem item) {
        if(item.isHasPriceRange()) {
            assertNotNull(item.getPriceLow());
            assertNotNull(item.getPriceHigh());
            assertTrue(item.getPriceLow() > 0);
            assertTrue(item.getPriceHigh() > 0);
        } else {
            assertNotNull(item.getPrice(), "Price is null for " + item.getTitle());
            assertTrue(item.getPrice() > 0, "Price is 0 for " + item.getTitle());
        }
    }

    private boolean isValidCondition(CharSequence condition) {
        return condition.equals("Pre-Owned") || condition.equals("Brand New") || condition.equals("New (Other)");
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
