package com.exchangerateprovider.constants;

public final class Constants {

    public static final String API_CURRENCY_LIST_REQUEST_URL
            = "https://api.nbrb.by/exrates/currencies";

    public static final String API_RATE_REQUEST_URL
            = "https://api.nbrb.by/exrates/rates?ondate={date}&periodicity=0";

    public static final String API_RATE_REQUEST_BY_CODE_URL
            = "https://api.nbrb.by/ExRates/Rates/Dynamics/{code}?startDate={date-previous-day}&endDate={date}";

    public static final String EMPTY_ARRAY_API_RESPONSE = "[]";

    public enum ApiExchangeRateJsonKey {

        CURRENCY_CODE("Cur_ID"),
        REQUEST_DATE("Date"),
        EXCHANGE_RATE("Cur_OfficialRate"),
        CURRENCY_NAME("Cur_Name");

        private final String type;

        ApiExchangeRateJsonKey(String type) {
            this.type = type;
        }

        public String get() {
            return type;
        }
    }
}
