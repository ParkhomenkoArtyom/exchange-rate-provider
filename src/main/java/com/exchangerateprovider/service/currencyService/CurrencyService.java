package com.exchangerateprovider.service.currencyService;

import com.exchangerateprovider.entity.Currency;

public interface CurrencyService {
    void saveListOfAvailableCurrencies();
    Currency getCurrencyByCode(Integer code);
    void clearCurrenciesList();
}
