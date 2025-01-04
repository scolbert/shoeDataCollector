package com.tcc.shoedatacollector.pageobjects;

import com.tcc.shoedatacollector.DTOs.SearchResultsItem;
import com.tcc.shoedatacollector.utilities.TitleUtilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class SearchBySellerPageObject {
    public List<SearchResultsItem> getSearchResultItems(WebDriver driver) {
        List<SearchResultsItem> result = new ArrayList<>();
        List<WebElement> titleBlocks = driver.findElements(By.cssSelector("div.s-item__title > span[role='heading']"));
        for (WebElement titleBlock : titleBlocks) {
            SearchResultsItem listing = new SearchResultsItem();
            String titleText = titleBlock.getAttribute("innerHTML");
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
