package com.exchangerateprovider.service.rateService;

import com.exchangerateprovider.entity.Rate;
import com.exchangerateprovider.responseDto.RateResponseDto;

public interface RateService {
    void saveRatesInStorage(Rate[] rates);
    Rate[] getRatesOnRequestDate(String date);
    Rate getRateFromStorageByCodeAndDate(Integer code, String date);
    Rate getRateFromApiByCodeAndDate(Integer code, String date);
    RateResponseDto rateToDto(Rate rate);
    Boolean checkIfRatesExistsByDate(String date);
}
