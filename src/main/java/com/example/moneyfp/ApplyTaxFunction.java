package com.example.moneyfp;

import java.math.BigDecimal;
import java.util.function.BiFunction;

public class ApplyTaxFunction implements BiFunction<BigDecimal, BigDecimal, BigDecimal> {
    @Override
    public BigDecimal apply(BigDecimal rate, BigDecimal amount) {
        return amount.multiply(rate).add(amount);
    }
}
