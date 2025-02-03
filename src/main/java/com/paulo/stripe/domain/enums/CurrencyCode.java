package com.paulo.stripe.domain.enums;

public enum CurrencyCode {
    USD("usd"), // United States Dollar
    EUR("eur"), // Euro
    GBP("gbp"), // British Pound
    JPY("jpy"), // Japanese Yen
    AUD("aud"), // Australian Dollar
    CAD("cad"), // Canadian Dollar
    CHF("chf"), // Swiss Franc
    CNY("cny"), // Chinese Yuan Renminbi
    SEK("sek"), // Swedish Krona
    NZD("nzd"), // New Zealand Dollar
    MXN("mxn"), // Mexican Peso
    SGD("sgd"), // Singapore Dollar
    HKD("hkd"), // Hong Kong Dollar
    NOK("nok"), // Norwegian Krone
    KRW("krw"), // South Korean Won
    TRY("try"), // Turkish Lira
    INR("inr"), // Indian Rupee
    RUB("rub"), // Russian Ruble
    BRL("brl"), // Brazilian Real
    ZAR("zar"); // South African Rand

    private final String value;

    CurrencyCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}