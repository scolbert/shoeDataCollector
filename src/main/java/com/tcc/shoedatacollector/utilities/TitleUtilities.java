package com.tcc.shoedatacollector.utilities;

import com.tcc.shoedatacollector.DTOs.SearchResultsItem;

public class TitleUtilities {
    public static String removeHtmlFromText(String text) {
//        return text.replaceAll("<!--.*?-->", "");
        return text.replaceAll("<.*?>", "");
    }

    public static String removeWordsNewListingFromText(String text) {
        return text.replaceAll("New Listing", "");
    }

    public static boolean hasShopOnEbayTitle(SearchResultsItem ignorableTitleItem) {
        return ignorableTitleItem.getTitle().contains("Shop on eBay");
    }
}
