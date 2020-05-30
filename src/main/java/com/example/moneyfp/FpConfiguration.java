package com.example.moneyfp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.function.BiFunction;
import java.util.function.Function;

@Configuration
public class FpConfiguration {

    @Bean
    public Function<BigDecimal, BigDecimal> calculateFinalPriceForListingPrice(
        String discountRateString,
        String taxRateString,
        BiFunction<BigDecimal, BigDecimal, BigDecimal> applyDiscountFunction,
        BiFunction<BigDecimal, BigDecimal, BigDecimal> applyTaxFunction,
        CalculateFinalPriceFunction calculateFinalPriceFunction
    ) {
        BigDecimal discountRate = new BigDecimal(discountRateString);
        BigDecimal taxRate = new BigDecimal(taxRateString);
        return generateCurriedCalculateFinalPrice(discountRate, taxRate, applyDiscountFunction, applyTaxFunction, calculateFinalPriceFunction);
    }

    private Function<BigDecimal, BigDecimal> generateCurriedCalculateFinalPrice(
        BigDecimal discountRate,
        BigDecimal taxRate,
        BiFunction<BigDecimal, BigDecimal, BigDecimal> applyDiscount,
        BiFunction<BigDecimal, BigDecimal, BigDecimal> applyTax,
        CalculateFinalPriceFunction calculateFinalPriceFunction
    ) {
        Function applyDiscountForAmount = curry(applyDiscount).apply(discountRate);
        Function applyTaxForAmount = curry(applyTax).apply(taxRate);
        Function calculateFinalPriceForListingPrice = (Function) curry(calculateFinalPriceFunction)
                .apply(applyDiscountForAmount)
                .apply(applyTaxForAmount);
        return calculateFinalPriceForListingPrice;
    }

    private Function<BigDecimal, Function<BigDecimal, BigDecimal>>
    curry(BiFunction<BigDecimal, BigDecimal, BigDecimal> function) {
        return t -> u -> function.apply(t, u);
    }

    private Function<Function<BigDecimal, BigDecimal>, Function<Function<BigDecimal, BigDecimal>, Function<BigDecimal, BigDecimal>>>
    curry(CalculateFinalPriceFunction function) {
        return applyDiscountForAmount -> applyTaxForAmount -> listingPrice ->
                function.apply(applyDiscountForAmount, applyTaxForAmount, listingPrice);
    }
}

