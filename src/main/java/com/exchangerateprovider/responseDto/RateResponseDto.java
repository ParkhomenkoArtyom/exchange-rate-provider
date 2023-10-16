package com.exchangerateprovider.responseDto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
@Schema(description = "Сущность курса валюты")
public record RateResponseDto(
        @Schema(description = "Название валюты", example = "Доллар США") String name,
        @Schema(description = "Аббревиатура валюты", example = "USD") String abbrev,
        @Schema(description = "Количество единиц иностранной валюты", example = "1") String scale,
        @Schema(description = "Курс валюты", example = "3.26") BigDecimal rate,
        @Schema(description = "Изменение курса в сравнении с прошедшим днем (UP - вырос, DOWN - уменьшился)",
        example =  "1.25% UP") String rateDynamic) {
}
