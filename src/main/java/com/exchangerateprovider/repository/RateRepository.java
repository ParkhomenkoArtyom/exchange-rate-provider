package com.exchangerateprovider.repository;

import com.exchangerateprovider.entity.Rate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RateRepository extends JpaRepository<Rate, Integer> {
    Optional<Rate> findRateByCodeAndDate(Integer code, String date);
    Optional<Rate> findFirstByDate(String date);
}
