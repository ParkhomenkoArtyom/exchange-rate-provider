package com.exchangerateprovider.controller;

import com.exchangerateprovider.responseDto.ExceptionResponseDto;
import com.exchangerateprovider.responseDto.RateResponseDto;
import com.exchangerateprovider.service.rateService.RateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@Tag(name = "Курс валют", description = "Получение информации о курсах валют")
@RestController
@RequiredArgsConstructor
@RequestMapping("/rate")
public class RateController {
    private final RateService rateService;

    @Operation(
            summary = "Получение и сохранение списка курсов валют",
            description = "Позволяет получить и сохранить список доступных курсов валют на заданную дату"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список курсов валют был получен и успешно сохранен", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Ошибка получения списка курсов валют через API", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseDto.class))}),
            @ApiResponse(responseCode = "503", description = "Ошибка соединения с API", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseDto.class))})})
    @PostMapping(path = "/{date}")
    public ResponseEntity<String> saveCurrencyRatesList(@PathVariable @Parameter(description = "Дата курсов валют") String date) {
        if (!rateService.checkIfRatesExistsByDate(date)) {
            rateService.saveRatesInStorage(rateService.getRatesOnRequestDate(date));
            return ResponseEntity.ok("List of exchange rates was successfully saved");
        }
        return ResponseEntity.ok("List of exchange rates for the requested date has already been received");
    }

    @Operation(
            summary = "Получение курса валюты",
            description = "Позволяет получить курс валюты по коду валюты на заданную дату"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RateResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Ошибка получения курса валюты через API", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseDto.class))}),
            @ApiResponse(responseCode = "503", description = "Ошибка соединения с API", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseDto.class))})})
    @GetMapping
    public ResponseEntity<RateResponseDto> getCurrencyRate(@RequestParam @Parameter(description = "Дата курса валюты") String date,
                                                           @RequestParam @Parameter(description = "Код валюты") Integer code) {
        RateResponseDto rateResponseDto = rateService.rateToDto(Objects.requireNonNullElseGet(
                rateService.getRateFromStorageByCodeAndDate(code, date), () ->
                        rateService.getRateFromApiByCodeAndDate(code, date))
        );
        return ResponseEntity.ok(rateResponseDto);
    }
}
