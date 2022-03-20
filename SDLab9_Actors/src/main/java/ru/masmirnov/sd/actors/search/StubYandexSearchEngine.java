package ru.masmirnov.sd.actors.search;

import com.google.gson.Gson;
import ru.masmirnov.sd.actors.search.ex.InvalidQueryException;

import java.util.List;
import java.util.stream.Collectors;

public class StubYandexSearchEngine extends SearchEngine {

    private int sleepMillisFrom, sleepMillisTo;

    public StubYandexSearchEngine(int sleepMillisFrom, int sleepMillisTo) {
        this.name = "yandex-stub";
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
            String url = "https://yandex.some-search-result-" + i + ".ru/?q=" + q;
            String title = "Результат поиска Яндекса " + i;
            String text = "Содержание сайта 'Результат поиска Яндекса " + i + "' ...";
            jsonSb.append("  {\n");
            jsonSb.append("   \"title\": " + "\"").append(title).append("\",\n");
            jsonSb.append("   \"link\": " + "\"").append(url).append("\",\n");
            jsonSb.append("   \"description\": " + "\"").append(text).append("\"\n");
            jsonSb.append("  }").append(i == num? "\n" : ",\n");
        }
        jsonSb.append(" ]\n");
        jsonSb.append("}\n");
        return jsonSb.toString();
    }

    @Override
    public List<SearchResult> fromJson(String json) {
        return new Gson().fromJson(json, YandexSearchResults.class)
                .items.stream()
                .map(ysr -> new SearchResult(ysr.link, ysr.title, ysr.description))
                .collect(Collectors.toList());
    }

    private static class YandexSearchResults {

        private List<YandexSearchResult> items;

        private static class YandexSearchResult {

            private String title;
            private String link;
            private String description;

        }

    }

}
