package com.exchangerateprovider.service.currencyService;

import com.exchangerateprovider.config.RestConfig;
import com.exchangerateprovider.entity.Currency;
import com.exchangerateprovider.exception.ApiConnectionException;
import com.exchangerateprovider.exception.CurrencyNotFoundException;
import com.exchangerateprovider.repository.CurrencyRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyRepository currencyRepository;
    private final RestTemplate restTemplate;

    /**
     * После запуска программы запрашивает с API информацию
     * о всех доступных курсах валют
     */
    @PostConstruct
    public void updateListOfAvailableCurrencies() {
        log.info("Updating list of available currencies...");
        clearCurrenciesList();
        saveListOfAvailableCurrencies();
        log.info("List of currencies was successfully updated");
    }

    @Override
    public void saveListOfAvailableCurrencies() {
        try {
            currencyRepository.saveAll(
                    Objects.requireNonNull(
                            restTemplate.exchange(
                                            RestConfig.API_CURRENCY_LIST_REQUEST_URL,
                                            HttpMethod.GET,
                                            null,
                                            new ParameterizedTypeReference<List<Currency>>() {
                                            })
                                    .getBody()));
        } catch (Exception e) {
            throw new ApiConnectionException("API connection error");
        }
    }

    @Override
    public Currency getCurrencyByCode(Integer code) {
        return currencyRepository.findCurrencyByCode(code).orElseThrow(
                () -> new CurrencyNotFoundException("Currency with code " + code + " not found"));
    }

    @Override
    public void clearCurrenciesList() {
        this.currencyRepository.deleteAll();
    }
}
