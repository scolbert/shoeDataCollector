package com.tcc.shoedatacollector.utilities;

import com.tcc.shoedatacollector.DTOs.SearchResultsItem;

public class TitleUtilities {
    public static String removeHtmlFromText(String text) {
        return text.replaceAll("<.*?>", "");
    }

    public static String removeWordsNewListingFromText(String text) {
        return text.replaceAll("New Listing", "");
    }

    // Ghost listings are listings that ebay has in their HTML but they don't actually show up on the page
    public static boolean isGhostListing(SearchResultsItem listing) {
        return listing.getTitle().contains("Shop on eBay");
    }
}
