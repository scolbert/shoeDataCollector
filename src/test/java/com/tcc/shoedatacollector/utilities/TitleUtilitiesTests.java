package com.tcc.shoedatacollector.utilities;

import com.tcc.shoedatacollector.DTOs.SearchResultsItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TitleUtilitiesTests {
    @Test
    public void removeHtmlFromText_removesHtmlComments_whenHtmlCommentsArePresent() {
        String text = "<!-- this is a comment -->This is not a comment";
        String result = TitleUtilities.removeHtmlFromText(text);
        assert(result.equals("This is not a comment"));
    }

    @Test
    public void removeHtmlFromText_removesOtherHtml_whenOtherHtmlIsPresent() {
        String text = "<span class=\"LIGHT_HIGHLIGHT\">New Listing</span>Nike Air Max 95 Womens Size 8.5 Shoes Sail Pink Guava Ice Athletic Sneakers\n";
        String result = TitleUtilities.removeHtmlFromText(text);
        System.out.println("---" + result + "---");
        assert(result.equals("New ListingNike Air Max 95 Womens Size 8.5 Shoes Sail Pink Guava Ice Athletic Sneakers\n"));
    }

    @Test
    public void removeHtmlFromText_doesNotRemoveHtml_whenHtmlIsNotPresent() {
        String text = "Nike Air Max 95 Womens Size 8.5 Shoes Sail Pink Guava Ice Athletic Sneakers\n";
        String result = TitleUtilities.removeHtmlFromText(text);
        System.out.println("---" + result + "---");
        assert(result.equals("Nike Air Max 95 Womens Size 8.5 Shoes Sail Pink Guava Ice Athletic Sneakers\n"));
    }

    @Test
    public void removeWordsNewListingFromText_removesNewListing_whenNewListingIsPresent() {
        String text = "New ListingNike Air Max 95 Womens Size 8.5 Shoes Sail Pink Guava Ice Athletic Sneakers\n";
        String result = TitleUtilities.removeWordsNewListingFromText(text);
        System.out.println("---" + result + "---");
        assert(result.equals("Nike Air Max 95 Womens Size 8.5 Shoes Sail Pink Guava Ice Athletic Sneakers\n"));
    }

    @Test
    public void removeWordsNewListingFromText_doesNotRemoveNewListing_whenNewListingIsNotPresent() {
        String text = "Nike Air Max 95 Womens Size 8.5 Shoes Sail Pink Guava Ice Athletic Sneakers\n";
        String result = TitleUtilities.removeWordsNewListingFromText(text);
        System.out.println("---" + result + "---");
        assert(result.equals("Nike Air Max 95 Womens Size 8.5 Shoes Sail Pink Guava Ice Athletic Sneakers\n"));
    }


    @Test
    public void hasShopOnEbayTitle_returnsTrue_whenItemHasTitleContainingShopOnEbay() {
        String ignorableTitle = "Shop on eBay";
        SearchResultsItem ignorableTitleItem = new SearchResultsItem();
        ignorableTitleItem.setTitle(ignorableTitle);

        assertTrue(TitleUtilities.hasShopOnEbayTitle(ignorableTitleItem));
    }

    @Test
    public void hasShopOnEbayTitle_returnsFalse_whenItemHasTitleDoesNotContainShopOnEbay() {
        String validTitle = "Nike Air Max 90 Mens 8";
        SearchResultsItem validTitleItem = new SearchResultsItem();
        validTitleItem.setTitle(validTitle);

        assertFalse(TitleUtilities.hasShopOnEbayTitle(validTitleItem));
    }

}
