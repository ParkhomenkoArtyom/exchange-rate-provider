package com.exchangerateprovider.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "rate")
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private Integer id;

    @JsonProperty("Cur_ID")
    @Column(name = "code", nullable = false, updatable = false)
    private Integer code;

    @JsonProperty("Date")
    @Column(name = "date", nullable = false, updatable = false)
    private String date;

    @JsonProperty("Cur_OfficialRate")
    @Column(name = "rate", nullable = false, updatable = false)
    private BigDecimal rate;

    @Column(name = "rate_last_day", nullable = false, updatable = false)
    private BigDecimal rateLastDay;
}
