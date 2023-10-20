package com.exchangerateprovider.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "currency")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private Integer id;

    @JsonProperty("Cur_ID")
    @Column(name = "code", nullable = false, updatable = false, unique = true)
    private Integer code;

    @JsonProperty("Cur_Name")
    @Column(name = "name", nullable = false, updatable = false)
    private String name;

    @JsonProperty("Cur_Abbreviation")
    @Column(name = "abbrev", nullable = false, updatable = false)
    private String abbrev;

    @JsonProperty("Cur_Scale")
    @Column(name = "scale", nullable = false, updatable = false)
    private String scale;
}
