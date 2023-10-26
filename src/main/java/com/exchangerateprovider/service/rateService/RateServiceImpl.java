package com.exchangerateprovider.service.rateService;

import com.exchangerateprovider.entity.Currency;
import com.exchangerateprovider.entity.Rate;
import com.exchangerateprovider.exception.ApiRequesterException;
import com.exchangerateprovider.service.apiRateParserService.ApiRateParserService;
import com.exchangerateprovider.repository.RateRepository;
import com.exchangerateprovider.responseDto.RateResponseDto;
import com.exchangerateprovider.service.currencyService.CurrencyService;
import com.exchangerateprovider.util.DateUtil;
import com.exchangerateprovider.util.RateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class RateServiceImpl implements RateService {
    private final ApiRateParserService apiRateParserService;
    private final RateRepository rateRepository;
    private final CurrencyService currencyService;

    @Override
    public void saveRatesInStorage(List<Rate> rates) {
        rateRepository.saveAll(rates);
    }

    @Override
    public List<Rate> getRatesOnRequestDate(String date) {
        if (!DateUtil.isValid(date))
            throw new ApiRequesterException("Requested date not valid, valid pattern: yyyy-mm-dd");
        log.info("Getting exchange rates list, date:{}", date);
        List<Rate> rates = apiRateParserService.getApiExchangeRates(date);
        log.info("List of exchange rates was successfully received, date:{}", date);
        return rates;
    }

    @Override
    public Rate getRateFromStorageByCodeAndDate(Integer code, String date) {
        if (!DateUtil.isValid(date))
            throw new ApiRequesterException("Requested date not valid, valid pattern: yyyy-mm-dd");
        log.info("Getting exchange rate, date:{}, code:{}", date, code);
        return rateRepository.findRateByCodeAndDate(code, date).orElse(null);
    }

    @Override
    public Rate getRateFromApiByCodeAndDate(Integer code, String date) {
        if (!DateUtil.isValid(date))
            throw new ApiRequesterException("Requested date not valid, valid pattern: yyyy-mm-dd");
        return apiRateParserService.getApiExchangeRateByCodeAndDate(code, date);
    }

    @Override
    public Boolean checkIfRatesExistsByDate(String date) {
        return rateRepository.findFirstByDate(date).isPresent();
    }

    @Override
    public RateResponseDto rateToDto(Rate rate) {
        Currency currency = currencyService.getCurrencyByCode(rate.getCode());
        String rateDynamic = rate.getRate().compareTo(rate.getRateLastDay()) > 0
                ? RateUtil.getRatePercentageDifference(rate.getRate(), rate.getRateLastDay()) + "% UP"
                : RateUtil.getRatePercentageDifference(rate.getRateLastDay(), rate.getRate()) + "% DOWN";

        RateResponseDto rateResponseDto = new RateResponseDto(
                currency.getName(),
                currency.getAbbrev(),
                currency.getScale(),
                rate.getRate(),
                rateDynamic);
        log.info("Exchange rate, rate:{},", rateResponseDto);
        return rateResponseDto;
    }
}
