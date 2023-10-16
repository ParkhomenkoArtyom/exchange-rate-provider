package com.exchangerateprovider.service.currencyService;

import com.exchangerateprovider.constants.Constants;
import com.exchangerateprovider.entity.Currency;
import com.exchangerateprovider.exception.exceptions.CurrencyNotFoundException;
import com.exchangerateprovider.repository.CurrencyRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyRepository currencyRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public void updateListOfAvailableCurrencies() {
        Currency[] currencies = restTemplate.getForObject(Constants.API_CURRENCY_LIST_REQUEST_URL, Currency[].class);
        assert currencies != null;
        this.currencyRepository.saveAll(Arrays.asList(currencies));

    }

    @Override
    public Currency getCurrencyByCode(Integer code) {
        return this.currencyRepository.findCurrencyByCode(code).orElseThrow(
                () -> new CurrencyNotFoundException("Валюта с кодом " + code + " не найдена"));
    }

    @Override
    public void clearCurrenciesList() {
        this.currencyRepository.deleteAll();
    }
}
