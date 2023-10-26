package com.exchangerateprovider.service.apiRateParserService;

import com.exchangerateprovider.config.RestConfig;
import com.exchangerateprovider.entity.Rate;
import com.exchangerateprovider.requester.ApiExchangeRateRequester;
import com.exchangerateprovider.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ApiRateParserServiceImpl implements ApiRateParserService {
    private final ApiExchangeRateRequester exchangeRateRequester;

    @Override
    public List<Rate> getApiExchangeRates(String date) {
        log.info("Getting API exchange rates list...");
        List<Rate> exchangeRates, exchangeRatesLastDay;
        exchangeRates = exchangeRateRequester.getApiDataList(RestConfig.API_RATES_REQUEST_URL, date);
        exchangeRatesLastDay = exchangeRateRequester.getApiDataList(RestConfig.API_RATES_REQUEST_URL, DateUtil.getPreviousDay(date));

        for (int i = 0; i < exchangeRates.size(); i++) {
            exchangeRates.get(i).setRateLastDay(exchangeRatesLastDay.get(i).getRate());
        }
        return exchangeRates;
    }

    @Override
    public Rate getApiExchangeRateByCodeAndDate(Integer code, String date) {
        log.info("Getting API exchange rate...");
        List<Rate> rateDynamics = exchangeRateRequester.getApiDataList(RestConfig.API_RATE_REQUEST_BY_CODE_URL, code, date);
        Rate rateOnRequestDay = rateDynamics.get(1);
        Rate rateOnPreviousDay = rateDynamics.get(0);
        rateOnRequestDay.setRateLastDay(rateOnPreviousDay.getRate());
        return rateOnRequestDay;
    }
}
