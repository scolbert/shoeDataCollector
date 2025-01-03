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
        List<WebElement> elements = driver.findElements(By.cssSelector("div.s-item__title > span[role='heading']"));
        for (WebElement element : elements) {
            SearchResultsItem item = new SearchResultsItem();
            String text = element.getAttribute("innerHTML");
            item.setTitle(TitleUtilities.removeHtmlFromText(text));
            if(TitleUtilities.hasShopOnEbayTitle(item)) {
                System.out.println("Skipping item with title: " + item.getTitle());
                continue;
            }
            result.add(item);
        }

        return result;
    }

}
