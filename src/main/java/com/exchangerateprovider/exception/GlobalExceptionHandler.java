package com.exchangerateprovider.exception;

import com.exchangerateprovider.responseDto.ExceptionResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDto> catchInvalidApiRequest(ApiRequesterException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new ExceptionResponseDto(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDto> catchCurrencyNotFoundException(CurrencyNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new ExceptionResponseDto(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDto> catchRateNotFoundException(RateNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new ExceptionResponseDto(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDto> catchApiConnectionException(ApiConnectionException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new ExceptionResponseDto(HttpStatus.SERVICE_UNAVAILABLE.value(), e.getMessage()), HttpStatus.SERVICE_UNAVAILABLE);
    }
}
