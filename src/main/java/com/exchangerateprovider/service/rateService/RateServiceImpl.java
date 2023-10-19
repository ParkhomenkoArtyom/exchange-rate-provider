package com.exchangerateprovider.service.rateService;

import com.exchangerateprovider.entity.Currency;
import com.exchangerateprovider.entity.Rate;
import com.exchangerateprovider.parser.ApiRateParser;
import com.exchangerateprovider.repository.RateRepository;
import com.exchangerateprovider.responseDto.RateResponseDto;
import com.exchangerateprovider.service.currencyService.CurrencyService;
import com.exchangerateprovider.util.RateUtil;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class RateServiceImpl implements RateService {
    private final ApiRateParser apiRateParser;
    private final RateRepository rateRepository;

    private final CurrencyService currencyService;

    public RateServiceImpl(ApiRateParser apiRateParser, RateRepository rateRepository, CurrencyService currencyService) {
        this.apiRateParser = apiRateParser;
        this.rateRepository = rateRepository;
        this.currencyService = currencyService;
    }

    @Override
    public void saveRatesInStorage(Rate[] rates) {
        this.rateRepository.saveAll(Arrays.asList(rates));
    }

    @Override
    public Rate[] getRatesOnRequestDate(String date) {
        return this.apiRateParser.getApiExchangeRates(date);
    }

    @Override
    public Rate getRateFromStorageByCodeAndDate(Integer code, String date) {
        return this.rateRepository.findRateByCodeAndDate(code, date).orElse(null);
    }

    @Override
    public Rate getRateFromApiByCodeAndDate(Integer code, String date) {
        return this.apiRateParser.getApiExchangeRateByCodeAndDate(code, date);
    }

    @Override
    public Boolean checkIfRatesExistsByDate(String date) {
        return this.rateRepository.findFirstByDate(date).isPresent();
    }

    @Override
    public RateResponseDto rateToDto(Rate rate) {
        Currency currency = this.currencyService.getCurrencyByCode(rate.getCode());
        String rateDynamic = rate.getRate().compareTo(rate.getRateLastDay()) > 0
                ? RateUtil.getRatePercentageDifference(rate.getRate(), rate.getRateLastDay()) + "% UP"
                : RateUtil.getRatePercentageDifference(rate.getRateLastDay(), rate.getRate()) + "% DOWN";
        return new RateResponseDto(
                currency.getName(),
                currency.getAbbrev(),
                currency.getScale(),
                rate.getRate(),
                rateDynamic);
    }
}
