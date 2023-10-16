package com.exchangerateprovider.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "rate")
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @Column(name = "code", nullable = false, updatable = false)
    private Integer code;

    @Column(name = "date", nullable = false, updatable = false)
    private String date;

    @Column(name = "rate", nullable = false, updatable = false)
    private BigDecimal rate;

    @Column(name = "rate_last_day", nullable = false, updatable = false)
    private BigDecimal rateLastDay;

    public Rate(Integer code, String date, BigDecimal rate, BigDecimal rateLastDay) {
        this.code = code;
        this.date = date;
        this.rate = rate;
        this.rateLastDay = rateLastDay;

    }

    public Rate() {
    }
}
