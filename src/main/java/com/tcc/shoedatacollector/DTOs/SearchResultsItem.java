package com.tcc.shoedatacollector.DTOs;

import lombok.Data;

@Data
public class SearchResultsItem {
    private String title;
    private String condition;
    private boolean hasPriceRange;
    private Float price;
    private Float priceLow;
    private Float priceHigh;
    private Float shippingPrice;
}
