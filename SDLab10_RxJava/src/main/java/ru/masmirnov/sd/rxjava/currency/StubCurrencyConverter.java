package ru.masmirnov.sd.rxjava.currency;

public class StubCurrencyConverter implements CurrencyConverter {

    public double convert(double price, Currency currency, Currency newCurrency) {
        return price * rublesIn(currency) / rublesIn(newCurrency);
    }

    private static double rublesIn(Currency currency) {
        switch (currency) {
            case RUB: return 1;
            case USD: return 132;
            case EUR: return 146;
            default: throw new IllegalStateException("Undefined currency: " + currency);
        }
    }
}
