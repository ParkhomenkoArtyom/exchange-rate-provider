package com.exchangerateprovider.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {
    public static final String API_CURRENCY_LIST_REQUEST_URL
            = "https://api.nbrb.by/exrates/currencies";

    public static final String API_RATES_REQUEST_URL
            = "https://api.nbrb.by/exrates/rates?ondate={date}&periodicity=0";

    public static final String API_RATE_REQUEST_BY_CODE_URL
            = "https://api.nbrb.by/ExRates/Rates/Dynamics/{code}?startDate={date-previous-day}&endDate={date}";

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder.build();
    }
}
