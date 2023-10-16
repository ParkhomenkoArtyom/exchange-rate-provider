package com.exchangerateprovider.exception;


import io.swagger.v3.oas.annotations.media.Schema;

public record ExceptionResponse(
        @Schema(description = "Код ошибки") Integer statusCode,
        @Schema(description = "Информация об ошибке") String message){}
