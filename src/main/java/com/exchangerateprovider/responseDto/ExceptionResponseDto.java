package com.exchangerateprovider.responseDto;


import io.swagger.v3.oas.annotations.media.Schema;

public record ExceptionResponseDto(
        @Schema(description = "Код ошибки") Integer statusCode,
        @Schema(description = "Информация об ошибке") String message){}
