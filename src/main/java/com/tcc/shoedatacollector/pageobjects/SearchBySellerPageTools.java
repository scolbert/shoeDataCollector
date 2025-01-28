package com.tcc.shoedatacollector.pageobjects;

import com.tcc.shoedatacollector.DTOs.SearchResultsItem;
import com.tcc.shoedatacollector.utilities.TitleUtilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class SearchBySellerPageTools {
    private final List<SearchResultsItem> result = new ArrayList<>();

    private List<WebElement> getListingElements(WebDriver sbsPage) {
        return sbsPage.findElements(By.className("s-item__wrapper"));
    }

    public List<SearchResultsItem> getListings(WebDriver sbsPage) {
        List<WebElement> listingElements = getListingElements(sbsPage);
        mapToSearchResultItems(listingElements);
        return result;
    }

    // TODO: convert this to a stream and use filter to remove ghost listings
    private void mapToSearchResultItems(List<WebElement> listingElements) {
        for (WebElement listingElement : listingElements) {
            SearchResultsItem listing = mapToSearchResultItem(listingElement);
            // Ghost listings have "Shop on eBay" in the title
            if(TitleUtilities.isGhostListing(listing)) {
                System.out.println("Skipping ghost listing");
                continue;
            }
            result.add(listing);
        }
    }

    private static SearchResultsItem mapToSearchResultItem(WebElement listingElement) {
        SearchResultsItem listing = new SearchResultsItem();

        String titleText = extractTitle(listingElement);

        listing.setTitle(titleText);
        return listing;
    }

    private static String extractTitle(WebElement listingElement) {
        String titleText = listingElement.findElement(By.cssSelector("div.s-item__title > span[role='heading']")).getAttribute("innerHTML");
        titleText =
                TitleUtilities.removeWordsNewListingFromText(TitleUtilities.removeHtmlFromText(titleText));
        return titleText;
    }

}

// TODO: Mock web elements in tests - https://medium.com/@M4tthe/selenium-and-wiremock-for-efficient-request-mocking-4f053f02e283
// Thoughts about this class's structure:
// - I should use a constructor to call a bunch of parse methods that extract the important data from the page - maybe it should just go to a mapper
// - I should add a method to get the next page of items