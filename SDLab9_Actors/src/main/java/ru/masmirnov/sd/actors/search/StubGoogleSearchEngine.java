package ru.masmirnov.sd.actors.search;

import com.google.gson.Gson;
import ru.masmirnov.sd.actors.search.ex.InvalidQueryException;

import java.util.List;
import java.util.stream.Collectors;

public class StubGoogleSearchEngine extends SearchEngine {

    private int sleepMillisFrom, sleepMillisTo;

    public StubGoogleSearchEngine(int sleepMillisFrom, int sleepMillisTo) {
        this.name = "google-stub";
        this.sleepMillisFrom = sleepMillisFrom;
        this.sleepMillisTo = sleepMillisTo;
    }

    @Override
    public String validateQuery(String q) throws InvalidQueryException {
        SearchEngineUtils.validate(q);
        return q.replaceAll(" ", "%20").toLowerCase();
    }

    @Override
    public String query(String q, int num) {
        SearchEngineUtils.rndSleep(sleepMillisFrom, sleepMillisTo);

        StringBuilder jsonSb = new StringBuilder("{" +
                " \"items\": [\n");
        for (int i = 1; i <= num; i++) {
            String url = "https://google.some-search-result-" + i + ".com/?q=" + q;
            String title = "Google search result " + i;
            String text = "Content of 'Google search result " + i + "' site ...";
            jsonSb.append("  {\n");
            jsonSb.append("   \"title\": " + "\"").append(title).append("\",\n");
            jsonSb.append("   \"link\": " + "\"").append(url).append("\",\n");
            jsonSb.append("   \"snippet\": " + "\"").append(text).append("\"\n");
            jsonSb.append("  }").append(i == num? "\n" : ",\n");
        }
        jsonSb.append(" ]\n");
        jsonSb.append("}\n");
        return jsonSb.toString();
    }

    @Override
    public List<SearchResult> fromJson(String json) {
        return new Gson().fromJson(json, GoogleSearchResults.class)
                .items.stream()
                .map(gsr -> new SearchResult(gsr.link, gsr.title, gsr.snippet))
                .collect(Collectors.toList());
    }

    private static class GoogleSearchResults {

        private List<GoogleSearchResult> items;

        private static class GoogleSearchResult {

            private String title;
            private String link;
            private String snippet;

        }

    }

}
