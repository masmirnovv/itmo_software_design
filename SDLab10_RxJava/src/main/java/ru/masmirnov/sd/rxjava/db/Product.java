package ru.masmirnov.sd.rxjava.db;

import org.bson.Document;
import ru.masmirnov.sd.rxjava.currency.Currency;

import java.util.List;
import java.util.Map;

import static ru.masmirnov.sd.rxjava.db.User.ID_COLUMN;

public class Product {

    public static final String NAME_COLUMN = "name";
    public static final String PRICE_COLUMN = "price";
    public static final String CURRENCY_COLUMN = "currency";

    private final String name;
    private final double price;
    private final Currency currency;


    public Product(String name, double price, Currency currency) {
        this.name = name;
        this.price = price;
        this.currency = currency;
    }

    public Product(Document doc) {
        this.name = doc.getString(NAME_COLUMN);
        this.price = doc.getDouble(PRICE_COLUMN);
        this.currency = Currency.valueOf(doc.getString(CURRENCY_COLUMN));
    }


    public Document toDocument() {
        return new Document(NAME_COLUMN, name)
                .append(PRICE_COLUMN, price)
                .append(CURRENCY_COLUMN, currency.toString());
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getPrice(Currency newCurrency) {
        return Currency.CONVERTER.convert(price, this.currency, newCurrency);
    }

    public Product convertPrice(Currency newCurrency) {
        return new Product(name, getPrice(newCurrency), newCurrency);
    }


    public static Product fromQueryParameters(Map<String, List<String>> queryParameters) {
        String name = queryParameters.get(NAME_COLUMN).get(0);
        double price = Double.parseDouble(queryParameters.get(PRICE_COLUMN).get(0));
        Currency currency = Currency.valueOf(queryParameters.get(Product.CURRENCY_COLUMN).get(0));
        return new Product(name, price, currency);
    }


    public String toString() {
        return String.format("Product{%s=%s,%s=%s,%s=%s}", NAME_COLUMN, name, PRICE_COLUMN, price, CURRENCY_COLUMN, currency);
    }

}
