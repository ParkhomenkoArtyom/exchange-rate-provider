package com.exchangerateprovider.parser;

import com.exchangerateprovider.entity.Rate;
import com.exchangerateprovider.exception.exceptions.ApiRequesterException;
import com.exchangerateprovider.requester.ApiExchangeRateRequester;
import com.exchangerateprovider.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import com.exchangerateprovider.constants.Constants;

@Slf4j
@Component
public class ApiRateParserImpl implements ApiRateParser {
    private final ApiExchangeRateRequester exchangeRateRequester;

    public ApiRateParserImpl(ApiExchangeRateRequester exchangeRateRequester) {
        this.exchangeRateRequester = exchangeRateRequester;
    }

    @Override
    public Rate[] getApiExchangeRates(String date) {
        log.info("Получение списка курсов валют через API...");
        String exchangeRates, exchangeRatesLastDay;
        exchangeRates = exchangeRateRequester.getApiExchangeRateJson(date);
        exchangeRatesLastDay = exchangeRateRequester
                .getApiExchangeRateJson(DateUtil.getPreviousDay(date));
        if (exchangeRates.equals(Constants.EMPTY_ARRAY_API_RESPONSE)
                || exchangeRatesLastDay.equals(Constants.EMPTY_ARRAY_API_RESPONSE))
            throw new ApiRequesterException("Ошибка получения списка курсов валют через API");
        return parseApiExchangeRates(exchangeRates, exchangeRatesLastDay);
    }

    @Override
    public Rate getApiExchangeRateByCodeAndDate(Integer code, String date) {
        log.info("Получение курса валюты через API...");
        JSONArray rateOnRequestDayAndPreviousDay = new JSONArray(exchangeRateRequester
                .getApiExchangeRateByCodeAndDateJson(code, date));
        if (rateOnRequestDayAndPreviousDay.isEmpty()) {
            throw new ApiRequesterException("Неверный запрос к API");
        }
        JSONObject rateOnRequestDay = rateOnRequestDayAndPreviousDay.getJSONObject(1);
        JSONObject rateOnPreviousDay = rateOnRequestDayAndPreviousDay.getJSONObject(0);

        return new Rate(
                rateOnRequestDay.getInt(Constants.ApiExchangeRateJsonKey.CURRENCY_CODE.get()),
                rateOnRequestDay.getString(Constants.ApiExchangeRateJsonKey.REQUEST_DATE.get())
                        .substring(0, 10),
                rateOnRequestDay.getBigDecimal(Constants.ApiExchangeRateJsonKey.EXCHANGE_RATE.get()),
                rateOnPreviousDay.getBigDecimal(Constants.ApiExchangeRateJsonKey.EXCHANGE_RATE.get())
        );
    }

    private Rate[] parseApiExchangeRates(String apiExchangeRates, String apiExchangeRatesLastDay) {
        JSONArray exchangeRates = new JSONArray(apiExchangeRates);
        JSONArray exchangeRatesLstDay = new JSONArray(apiExchangeRatesLastDay);
        Rate[] rates = new Rate[exchangeRates.length()];

        for (int i = 0; i < exchangeRates.length(); i++) {
            JSONObject rateJson = exchangeRates.getJSONObject(i);
            JSONObject rateLastDayJson = exchangeRatesLstDay.getJSONObject(i);
            rates[i] = new Rate(
                    rateJson.getInt(Constants.ApiExchangeRateJsonKey.CURRENCY_CODE.get()),
                    rateJson.getString(Constants.ApiExchangeRateJsonKey.REQUEST_DATE.get())
                            .substring(0, 10),
                    rateJson.getBigDecimal(Constants.ApiExchangeRateJsonKey.EXCHANGE_RATE.get()),
                    rateLastDayJson.getBigDecimal(Constants.ApiExchangeRateJsonKey.EXCHANGE_RATE.get()
                    ));
        }
        return rates;
    }
}
