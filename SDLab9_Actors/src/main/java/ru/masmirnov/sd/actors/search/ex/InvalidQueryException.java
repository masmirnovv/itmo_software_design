package ru.masmirnov.sd.actors.search.ex;

public class InvalidQueryException extends Exception {

    public InvalidQueryException(String q) {
        super("Invalid query: " + q);
    }

}
