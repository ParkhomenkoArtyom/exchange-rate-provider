package com.exchangerateprovider.service.currencyService;

import com.exchangerateprovider.entity.Currency;

public interface CurrencyService {
    void updateListOfAvailableCurrencies();
    Currency getCurrencyByCode(Integer code);
    void clearCurrenciesList();
}
