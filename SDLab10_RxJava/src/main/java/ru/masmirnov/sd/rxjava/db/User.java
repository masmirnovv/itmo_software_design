package ru.masmirnov.sd.rxjava.db;

import org.bson.Document;
import ru.masmirnov.sd.rxjava.currency.Currency;

import java.util.List;
import java.util.Map;

public class User {

    public static final String ID_COLUMN = "id";
    public static final String CURRENCY_COLUMN = "currency";

    private final int id;
    private final Currency currency;


    public User(int id, Currency currency) {
        this.id = id;
        this.currency = currency;
    }

    public User(Document doc) {
        this.id = doc.getInteger(ID_COLUMN);
        this.currency = Currency.valueOf(doc.getString(CURRENCY_COLUMN));
    }


    public Document toDocument() {
        return new Document(ID_COLUMN, id)
                .append(CURRENCY_COLUMN, currency.toString());
    }

    public int getId() {
        return id;
    }

    public Currency getCurrency() {
        return currency;
    }


    public static User fromQueryParameters(Map<String, List<String>> queryParameters) {
        int id = Integer.parseInt(queryParameters.get(ID_COLUMN).get(0));
        Currency currency = Currency.valueOf(queryParameters.get(User.CURRENCY_COLUMN).get(0));
        return new User(id, currency);
    }


    public String toString() {
        return String.format("User{%s=%s,%s=%s}", ID_COLUMN, id, CURRENCY_COLUMN, currency);
    }

}
