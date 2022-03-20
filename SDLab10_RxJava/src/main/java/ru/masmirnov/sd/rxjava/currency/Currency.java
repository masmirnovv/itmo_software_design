package ru.masmirnov.sd.rxjava.currency;

public enum Currency {

    RUB, USD, EUR;

    public static final CurrencyConverter CONVERTER = new StubCurrencyConverter();

}
