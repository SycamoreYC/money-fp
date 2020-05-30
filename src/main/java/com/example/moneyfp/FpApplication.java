package com.example.moneyfp;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.function.Function;


@Component("fpApplication")
public class FpApplication {
    private final Function<BigDecimal, BigDecimal> calculateFinalPriceForListingPrice;

    public FpApplication(Function<BigDecimal, BigDecimal> calculateFinalPriceForListingPrice) {
        this.calculateFinalPriceForListingPrice = calculateFinalPriceForListingPrice;
    }

    public BigDecimal run(BigDecimal listingPrice) {
        return calculateFinalPriceForListingPrice.apply(listingPrice);
    }
}
