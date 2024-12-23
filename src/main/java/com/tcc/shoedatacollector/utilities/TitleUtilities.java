package com.tcc.shoedatacollector.utilities;

import com.tcc.shoedatacollector.DTOs.SearchResultsItem;

public class TitleUtilities {
    public static String removeHtmlCommentsFromText(String text) {
        return text.replaceAll("<!--.*?-->", "");
    }

    public static boolean hasShopOnEbayTitle(SearchResultsItem ignorableTitleItem) {
        return ignorableTitleItem.getTitle().contains("Shop on eBay");
    }
}
