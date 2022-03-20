package ru.masmirnov.sd.actors.search;

import ru.masmirnov.sd.actors.search.ex.InvalidQueryException;

import java.util.List;

public abstract class SearchEngine {

    protected String name;

    public String getName() {
        return name;
    }


    public abstract String validateQuery(String q) throws InvalidQueryException;

    public abstract String query(String q, int num);

    public abstract List<SearchResult> fromJson(String json);


    public List<SearchResult> search(String q, int num) throws InvalidQueryException {
        if (num < 0) {
            throw new InvalidQueryException("Negative search results number: " + num);
        }
        return fromJson(query(validateQuery(q), num));
    }

}
