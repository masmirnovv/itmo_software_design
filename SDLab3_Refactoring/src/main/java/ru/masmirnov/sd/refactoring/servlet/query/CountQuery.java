package ru.masmirnov.sd.refactoring.servlet.query;

import ru.masmirnov.sd.refactoring.*;

import java.util.Optional;

public class CountQuery extends Query<Optional<Long>> {

    public void executeQuery() {
        queryResult = DB.executeSQLQuery(DB.CALC_COUNT, DB::extractLong);
    }

    public void initRespond() {
        re.addLine("Number of products: ");
    }

    public void putResultInRespond() {
        queryResult.ifPresent(re::addLine);
    }

}