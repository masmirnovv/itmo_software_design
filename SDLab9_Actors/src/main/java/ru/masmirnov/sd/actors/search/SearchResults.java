package ru.masmirnov.sd.actors.search;

import ru.masmirnov.sd.actors.search.ex.InvalidQueryException;

import java.util.List;

public class SearchResults {

    private String engineName;
    private List<SearchResult> results;
    private InvalidQueryException ex;
    private boolean receiveTimeoutExceeded = false;

    public static final SearchResults RECEIVE_TIMEOUT_EXCEEDED = new SearchResults();

    private SearchResults() {
        this.receiveTimeoutExceeded = true;
    }

    public SearchResults(List<SearchResult> result, String engineName) {
        this.results = result;
        this.engineName = engineName;
    }

    public SearchResults(InvalidQueryException ex, String engineName) {
        this.ex = ex;
        this.engineName = engineName;
    }

    public List<SearchResult> getResults() {
        return results;
    }

    public InvalidQueryException getException() {
        return ex;
    }

    public boolean receiveTimeoutExceeded() {
        return receiveTimeoutExceeded;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Engine: ").append(engineName).append("\n");
        if (results != null) {
            sb.append("Successfully done.\n");
            for (SearchResult res : results) {
                sb.append(res);
            }
        } else if (ex != null) {
            sb.append(ex.getMessage()).append("\n");
        } else if (receiveTimeoutExceeded) {
            sb.append("Receive timeout exceeded.\n");
        }
        return sb.toString();
    }

}
