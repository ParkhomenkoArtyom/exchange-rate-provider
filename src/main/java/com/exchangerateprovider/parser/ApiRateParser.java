package com.exchangerateprovider.parser;

import com.exchangerateprovider.entity.Rate;

public interface ApiRateParser {
    Rate[] getApiExchangeRates(String date);
    Rate getApiExchangeRateByCodeAndDate(Integer code, String date);
}
