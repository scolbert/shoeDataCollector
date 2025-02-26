package com.tcc.shoedatacollector.pageobjects;

import com.tcc.shoedatacollector.DTOs.SearchResultsItem;
import com.tcc.shoedatacollector.utilities.TitleUtilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

//TODO extract the logic that does not simply get the elements from the page into separate methods
// The main method should extract the string and separate methods should clean up the strings. This way the
// clean up methods can be tested separately.
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
        String conditionText = extractCondition(listingElement);

        Float price = null;
        Float priceHigh = null;
        Float priceLow = null;
        if(hasPriceRange(listingElement)) {  // TODO I could make these methods return null and then remove the if statement
            priceHigh = extractPriceHigh(listingElement);
            priceLow = extractPriceLow(listingElement);
        } else {
            price = extractPrice(listingElement);
        }

        listing.setTitle(titleText);
        listing.setCondition(conditionText);

        if(hasPriceRange(listingElement)) {  // TODO I could assign the value directly to the listing without the if statement
            listing.setPriceLow(priceLow);
            listing.setPriceHigh(priceHigh);
            listing.setHasPriceRange(true);
        } else {
            listing.setPrice(price);
            listing.setHasPriceRange(false);
        }

        listing.setShippingPrice(extractShippingPrice(listingElement));
        return listing;
    }

    // TODO: Can I remove boilerplate try catch blocks by using spring AOP?
    // TODO: Can I remove boilerplate try catch blocks by using a custom exception handler?
    // TODO: When I find exceptions persist them in a way that I can find them later. I am sure I will find some after the program is live.
    // TODO: Standardize and clean up error messages
    // TODO: Replace println statements with logging
    private static String extractTitle(WebElement listingElement) {
        try {
            String titleText = listingElement.findElement(By.cssSelector("div.s-item__title > span[role='heading']")).getAttribute("innerHTML");
            titleText =
                    TitleUtilities.removeWordsNewListingFromText(TitleUtilities.removeHtmlFromText(titleText));
            return titleText;
        } catch (Exception e) {
            // TODO log errors that are because of the page structure changing or because I haven't accounted for a value
            System.out.println("Error extracting title for " + listingElement.getAttribute("innerHTML") + " " + e.getMessage());
        }
        return "error extracting title";
    }

    private static String extractCondition(WebElement listingElement) {
        try {
            return listingElement.findElement(By.cssSelector("div.s-item__subtitle > span[class='SECONDARY_INFO']")).getAttribute("innerHTML");
        } catch (Exception e) {
            // TODO log errors that are because of the page structure changing or because I haven't accounted for a value
            String titleText = listingElement.findElement(By.cssSelector("div.s-item__title > span[role='heading']")).getAttribute("innerHTML");
            titleText =
                    TitleUtilities.removeWordsNewListingFromText(TitleUtilities.removeHtmlFromText(titleText));
            System.out.println("Error extracting condition for " + titleText + " " + e.getMessage());
        }
        return "error extracting condition";
    }

    private static boolean hasPriceRange(WebElement listingElement) {
        try {
            return listingElement.findElement(By.cssSelector("span.s-item__price")).getText().contains("to");
        } catch (Exception e) {
            // TODO log errors that are because of the page structure changing or because I haven't accounted for a value
            String titleText = listingElement.findElement(By.cssSelector("div.s-item__title > span[role='heading']")).getAttribute("innerHTML");
            titleText =
                    TitleUtilities.removeWordsNewListingFromText(TitleUtilities.removeHtmlFromText(titleText));
            System.out.println("Error extracting hasPriceRange for " + titleText + " " + e.getMessage());
        }
        return false;
    }

    private static Float extractPrice(WebElement listingElement) {
        try {
        String priceText = listingElement.findElement(By.cssSelector("span.s-item__price")).getAttribute("innerHTML");
        priceText = TitleUtilities.removeHtmlFromText(priceText);
        return Float.parseFloat(priceText.substring(1));
        } catch (Exception e) {
            // TODO log errors that are because of the page structure changing or because I haven't accounted for a value
            String titleText = listingElement.findElement(By.cssSelector("div.s-item__title > span[role='heading']")).getAttribute("innerHTML");
            titleText =
                    TitleUtilities.removeWordsNewListingFromText(TitleUtilities.removeHtmlFromText(titleText));
            System.out.println("Error extracting price for " + titleText + " " + e.getMessage());
        }
        return -1.0f;
    }

    // TODO: There has got to be a cleaner way to get the title into the logs.
    private static Float extractPriceLow(WebElement listingElement) {
        try {
        String priceText = listingElement.findElement(By.cssSelector("span.s-item__price")).getAttribute("innerHTML");
        priceText = TitleUtilities.removeHtmlFromText(priceText);
        return Float.parseFloat(priceText.substring(1, priceText.indexOf("to") - 1));
        } catch (Exception e) {
            // TODO log errors that are because of the page structure changing or because I haven't accounted for a value
            String titleText = listingElement.findElement(By.cssSelector("div.s-item__title > span[role='heading']")).getAttribute("innerHTML");
            titleText =
                    TitleUtilities.removeWordsNewListingFromText(TitleUtilities.removeHtmlFromText(titleText));
            System.out.println("Error extracting priceLow for " + titleText + " " + e.getMessage());
        }
        return -1.0f;
    }

    private static Float extractPriceHigh(WebElement listingElement) {
        try {
            String priceText = listingElement.findElement(By.cssSelector("span.s-item__price")).getAttribute("innerHTML");
            priceText = TitleUtilities.removeHtmlFromText(priceText);
            return Float.parseFloat(priceText.substring(priceText.indexOf("to") + 4)); // 4 is the length of " to $"
        } catch (Exception e) {
            // TODO log errors that are because of the page structure changing or because I haven't accounted for a value
            String titleText = listingElement.findElement(By.cssSelector("div.s-item__title > span[role='heading']")).getAttribute("innerHTML");
            titleText =
                    TitleUtilities.removeWordsNewListingFromText(TitleUtilities.removeHtmlFromText(titleText));
            System.out.println("Error extracting priceLow for " + titleText + " " + e.getMessage());
        }
        return -1.0f;
    }

    // TODO I need to rename TitleUtilities to something more generic
    // TODO It would be cleaner to clean up the text in one place and to parseFloat on another line. I am combining tasks here so it isn't as clear.

    private static Float extractShippingPrice(WebElement listingElement) {
        try {
            if(listingElement.findElement(By.cssSelector("div.s-item__title > span[role='heading']")).getAttribute("innerHTML").contains("Buy on eBay")) {
                return -1.0f;
            }
            String shippingPriceText = listingElement.findElement(By.cssSelector("span.s-item__shipping")).getAttribute("innerHTML");
            shippingPriceText = TitleUtilities.removeHtmlFromText(shippingPriceText);
            if(shippingPriceText.toUpperCase().contains("FREE")) {
                return 0.0f;
            }
            return Float.parseFloat(shippingPriceText.strip().substring(2, shippingPriceText.indexOf(" "))); // 2 is the length of "+$"
        } catch (Exception e) {
            // TODO log errors that are because of the page structure changing or because I haven't accounted for a value
            // TODO Some listings have "Buy on eBay" in the title. I should skip those entirely for tests
            String titleText = listingElement.findElement(By.cssSelector("div.s-item__title > span[role='heading']")).getAttribute("innerHTML");
            titleText =
                    TitleUtilities.removeWordsNewListingFromText(TitleUtilities.removeHtmlFromText(titleText));
            System.out.println("Error extracting shippingPrice for " + titleText + " Message: " + e.getMessage());
        }
        return -1.0f;
    }

}

// TODO: Mock web elements in tests - https://medium.com/@M4tthe/selenium-and-wiremock-for-efficient-request-mocking-4f053f02e283
// Thoughts about this class's structure:
// - I should use a constructor to call a bunch of parse methods that extract the important data from the page - maybe it should just go to a mapper
// - I should add a method to get the next page of items