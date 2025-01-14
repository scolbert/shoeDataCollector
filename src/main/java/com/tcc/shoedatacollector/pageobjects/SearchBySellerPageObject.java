package com.tcc.shoedatacollector.pageobjects;

import com.tcc.shoedatacollector.DTOs.SearchResultsItem;
import com.tcc.shoedatacollector.utilities.TitleUtilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class SearchBySellerPageObject {
    private final List<SearchResultsItem> result = new ArrayList<>();

    public List<WebElement> getListingElements(WebDriver driver) {
        return driver.findElements(By.className("s-item__wrapper"));
    }

    public List<SearchResultsItem> getSearchResultItems(WebDriver driver) {
        List<WebElement> listingElements = getListingElements(driver);
        for (WebElement listingElement : listingElements) {
            SearchResultsItem listing = new SearchResultsItem();
            String titleText = listingElement.findElement(By.cssSelector("div.s-item__title > span[role='heading']")).getAttribute("innerHTML");
            String cleanedUpTitle =
                    TitleUtilities.removeWordsNewListingFromText(
                    TitleUtilities.removeHtmlFromText(titleText));
            listing.setTitle(cleanedUpTitle);
            // Skip adding items to the list if they have "Shop on eBay" in the title
            if(TitleUtilities.hasShopOnEbayTitle(listing)) {
                System.out.println("Skipping item with title: " + listing.getTitle());
                continue;
            }
            result.add(listing);
        }

        return result;
    }

}
// Thoughts about this class's structure:
// - I need to move the items array list to the class level.
// - I need to move the driver to the class level.
// - I should use a constructor to call a bunch of parse methods that extract the important data from the page - maybe it should just go to a mapper
// - the first parse method should parse each listing into its own html element so that I can ensure that fields match the auction

// - The data should be returned as a List<Item> so that users can work with items in the list
// - I should add a method to get the next page of items