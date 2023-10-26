package com.exchangerateprovider.requester;

import com.exchangerateprovider.entity.Rate;
import com.exchangerateprovider.exception.ApiConnectionException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ApiExchangeRateRequesterImpl implements ApiExchangeRateRequester {
    private final RestTemplate restTemplate;

    @Override
    public List<Rate> getApiDataList(String requestUrl, Object... uriVariables) {
        try {
            return restTemplate.exchange(
                    requestUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Rate>>() {
                    },
                    uriVariables).getBody();
        } catch (Exception e) {
            throw new ApiConnectionException("API connection error");
        }
    }
}
