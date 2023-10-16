package com.exchangerateprovider.requester;

public interface ApiExchangeRateRequester {
    String getApiExchangeRateJson(String date);
    String getApiExchangeRateByCodeAndDateJson(Integer code, String date);

}
