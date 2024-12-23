package com.tcc.shoedatacollector.utilities;

import com.tcc.shoedatacollector.DTOs.SearchResultsItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTitleUtilities {
    @Test
    public void removeHtmlCommentsFromText_removesHtmlComments_whenHtmlCommentsArePresent() {
        String text = "<!-- this is a comment -->This is not a comment";
        String result = TitleUtilities.removeHtmlCommentsFromText(text);
        assert(result.equals("This is not a comment"));
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
