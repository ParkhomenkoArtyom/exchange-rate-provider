package com.exchangerateprovider.service.apiRateParserService;

import com.exchangerateprovider.entity.Rate;

import java.util.List;

public interface ApiRateParserService {
    Rate getApiExchangeRateByCodeAndDate(Integer code, String date);
    List<Rate> getApiExchangeRates(String date);
}
