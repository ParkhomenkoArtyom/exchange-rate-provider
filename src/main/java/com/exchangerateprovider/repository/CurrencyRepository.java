package com.exchangerateprovider.repository;

import com.exchangerateprovider.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
    Optional<Currency> findCurrencyByCode(Integer code);
}
