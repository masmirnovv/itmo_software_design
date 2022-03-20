package ru.masmirnov.sd.rxjava.currency;

public interface CurrencyConverter {

    double convert(double price, Currency currency, Currency newCurrency);

}
