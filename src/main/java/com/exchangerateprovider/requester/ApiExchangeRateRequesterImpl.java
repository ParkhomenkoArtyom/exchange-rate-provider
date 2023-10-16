package com.exchangerateprovider.requester;

import com.exchangerateprovider.exception.exceptions.ApiConnectionException;
import com.exchangerateprovider.exception.exceptions.ApiRequesterException;
import com.exchangerateprovider.util.DateUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.exchangerateprovider.constants.Constants;

@Component
public class ApiExchangeRateRequesterImpl implements ApiExchangeRateRequester {
    RestTemplate restTemplate = new RestTemplate();

    @Override
    public String getApiExchangeRateJson(String date) {
        if (!DateUtil.isValid(date))
            throw new ApiRequesterException("Ошибка получения списка курсов валют через API");
        try {
            return restTemplate.getForObject(Constants.API_RATE_REQUEST_URL, String.class, date);
        } catch (Exception e) {
            throw new ApiConnectionException("Ошибка соединения с API");
        }
    }

    @Override
    public String getApiExchangeRateByCodeAndDateJson(Integer code, String date) {
        if (!DateUtil.isValid(date))
            throw new ApiRequesterException("Ошибка получения курса валюты через API");
        try {
            return restTemplate
                    .getForObject(
                            Constants.API_RATE_REQUEST_BY_CODE_URL,
                            String.class, code,
                            DateUtil.getPreviousDay(date), date
                    );
        } catch (Exception e) {
            throw new ApiConnectionException("Ошибка соединения с API");
        }
    }
}
