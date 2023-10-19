package com.exchangerateprovider.controller;

import com.exchangerateprovider.exception.ExceptionResponse;
import com.exchangerateprovider.responseDto.RateResponseDto;
import com.exchangerateprovider.service.currencyService.CurrencyService;
import com.exchangerateprovider.service.rateService.RateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.Objects;

@Slf4j
@Tag(name = "Курс валют", description = "Получение информации о курсах валют")
@RestController
@RequestMapping("/rate")
public class RateController {
    private final RateService rateService;
    private final CurrencyService currencyService;

    public RateController(RateService rateService, CurrencyService currencyService) {
        this.rateService = rateService;
        this.currencyService = currencyService;
    }

    /**
     * После запуска программы запрашивает с API информацию
     * о всех доступных курсах валют
     */
    @PostConstruct
    private void updateListOfAvailableCurrencies() {
        log.info("Обновление списка доступных курсов валют...");
        this.currencyService.clearCurrenciesList();
        this.currencyService.updateListOfAvailableCurrencies();
    }


    @Operation(
            summary = "Получение и сохранение списка курсов валют",
            description = "Позволяет получить и сохранить список доступных курсов валют на заданную дату"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список курсов валют был получен и успешно сохранен", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Ошибка получения списка курсов валют через API", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))}),
            @ApiResponse(responseCode = "503", description = "Ошибка соединения с API", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))})})
    @PostMapping(path = "/{date}")
    public String saveCurrencyRatesList(@PathVariable @Parameter(description = "Дата курсов валют") String date) {
        log.info("Получение списка курсов валют, date:{}", date);
        if (!this.rateService.checkIfRatesExistsByDate(date)) {
            this.rateService.saveRatesInStorage(this.rateService.getRatesOnRequestDate(date));
            log.info("Список курсов валют был получен и успешно сохранен, date:{}", date);
            return "Список курсов валют был успешно сохранен";
        }
        return "Список курсов валют на запрашиваемую дату уже получен";
    }

    @Operation(
            summary = "Получение курса валюты",
            description = "Позволяет получить курс валюты по коду валюты на заданную дату"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RateResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Ошибка получения курса валюты через API", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))}),
            @ApiResponse(responseCode = "503", description = "Ошибка соединения с API", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))})})
    @GetMapping
    public RateResponseDto getCurrencyRate(@RequestParam @Parameter(description = "Дата курса валюты") String date,
                                           @RequestParam @Parameter(description = "Код валюты") Integer code) {
        log.info("Получение курса валюты, date:{}, code:{}", date, code);
        RateResponseDto rateResponseDto = rateService.rateToDto(Objects.requireNonNullElseGet(
                rateService.getRateFromStorageByCodeAndDate(code, date), () ->
                        rateService.getRateFromApiByCodeAndDate(code, date))
        );
        log.info("Полученный курс валюты, rate:{}", rateResponseDto);
        return rateResponseDto;
    }
}
