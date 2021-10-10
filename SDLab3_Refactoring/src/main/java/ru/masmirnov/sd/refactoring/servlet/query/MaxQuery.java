package ru.masmirnov.sd.refactoring.servlet.query;

import ru.masmirnov.sd.refactoring.*;

import java.util.List;

public class MaxQuery extends Query<List<Product>> {

    public void executeQuery() {
        queryResult = DB.executeSQLQuery(DB.SELECT_MAX, DB::collectProducts);
    }

    public void initRespond() {
        re.addHeader("Product with max price: ", 1);
    }

    public void putResultInRespond() {
        for (Product max : queryResult) {
            re.addLine(max.getName() + "\t" + max.getPrice() + "</br>");
        }
    }

}