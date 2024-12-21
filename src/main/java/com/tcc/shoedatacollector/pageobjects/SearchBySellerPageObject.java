package com.tcc.shoedatacollector.pageobjects;

import com.tcc.shoedatacollector.DTOs.SearchResultsItem;
import org.openqa.selenium.WebDriver;

public class SearchBySellerPageObject {
    public SearchResultsItem[] getSearchResultItems(WebDriver driver) {
        SearchResultsItem[] result = new SearchResultsItem[1];
        SearchResultsItem item = new SearchResultsItem();
        result[0] = item;
        return result;
    }
}
