package com.exchangerateprovider.requester;

import com.exchangerateprovider.entity.Rate;

import java.util.List;

public interface ApiExchangeRateRequester {
    List<Rate> getApiDataList(String requestUrl, Object... uriVariables);
}
