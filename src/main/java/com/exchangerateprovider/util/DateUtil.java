package com.exchangerateprovider.util;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class DateUtil {
    public static String getPreviousDay(String currentDay) {
        LocalDate localDate;
        try {
            localDate = LocalDate.parse(currentDay).minusDays(1);
        } catch (DateTimeParseException e) {
            return null;
        }
        return localDate.toString();
    }

    public static Boolean isValid(String date) {
        try {
            LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("uuuu-MM-dd")
                    .withResolverStyle(ResolverStyle.STRICT));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
