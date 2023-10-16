package com.exchangerateprovider.exception;

import com.exchangerateprovider.exception.exceptions.ApiConnectionException;
import com.exchangerateprovider.exception.exceptions.ApiRequesterException;
import com.exchangerateprovider.exception.exceptions.CurrencyNotFoundException;
import com.exchangerateprovider.exception.exceptions.RateNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> catchInvalidApiRequest(ApiRequesterException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> catchCurrencyNotFoundException(CurrencyNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> catchRateNotFoundException(RateNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> catchApiConnectionException(ApiConnectionException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new ExceptionResponse(HttpStatus.SERVICE_UNAVAILABLE.value(), e.getMessage()), HttpStatus.SERVICE_UNAVAILABLE);
    }
}
