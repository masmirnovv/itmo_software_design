package ru.masmirnov.sd.refactoring.servlet.query;

import ru.masmirnov.sd.refactoring.CustomHttpResponse;

public abstract class Query<T> {

    protected CustomHttpResponse re = new CustomHttpResponse();
    protected T queryResult;

    public static Query<?> init(String command) {
        switch (command) {
            case "min":
                return new MinQuery();
            case "max":
                return new MaxQuery();
            case "sum":
                return new SumQuery();
            case "count":
                return new CountQuery();
            default:
                return new UnknownCommandQuery();
        }
    }

    public CustomHttpResponse execute() {
        executeQuery();
        initRespond();
        putResultInRespond();
        return re;
    }

    abstract public void executeQuery();

    abstract public void initRespond();

    abstract public void putResultInRespond();

}
