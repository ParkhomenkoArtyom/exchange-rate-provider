package com.exchangerateprovider.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RateUtil {
    public static BigDecimal getRatePercentageDifference(BigDecimal num1, BigDecimal num2) {
        return num1
                .subtract(num2)
                .divide(num2, RoundingMode.HALF_UP)
                .multiply(new BigDecimal(100));
    }
}