package ru.masmirnov.sd.actors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.masmirnov.sd.actors.aggregator.SearchAggregator;
import ru.masmirnov.sd.actors.search.SearchEngine;
import ru.masmirnov.sd.actors.search.SearchResults;
import ru.masmirnov.sd.actors.search.StubGoogleSearchEngine;
import ru.masmirnov.sd.actors.search.StubYandexSearchEngine;

import java.util.List;

public class SearchAggregatorTest {

    private static final SearchEngine FAST_GOOGLE_STUB = new StubGoogleSearchEngine(0, 0);
    private static final SearchEngine GOOGLE_STUB = new StubGoogleSearchEngine(100, 200);
    private static final SearchEngine BAD_GOOGLE_STUB = new StubGoogleSearchEngine(Integer.MAX_VALUE, Integer.MAX_VALUE);

    private static final SearchEngine FAST_YANDEX_STUB = new StubYandexSearchEngine(0, 0);
    private static final SearchEngine YANDEX_STUB = new StubYandexSearchEngine(100, 200);
    private static final SearchEngine BAD_YANDEX_STUB = new StubYandexSearchEngine(Integer.MAX_VALUE, Integer.MAX_VALUE);

    @Test
    public void bothFastEnginesTest() {
        List<SearchResults> results = new SearchAggregator(FAST_GOOGLE_STUB, FAST_YANDEX_STUB)
                .aggregate("test 1", 5, 1000);
        Assertions.assertEquals(results.size(), 2);
        Assertions.assertNotEquals(results.get(0).getResults(), null);
        Assertions.assertNotEquals(results.get(1).getResults(), null);
        Assertions.assertEquals(results.get(0).getResults().size(), 5);
        Assertions.assertEquals(results.get(1).getResults().size(), 5);
    }

    @Test
    public void bothAverageEnginesTest() {
        List<SearchResults> results = new SearchAggregator(GOOGLE_STUB, YANDEX_STUB)
                .aggregate("test 2", 10, 1000);
        Assertions.assertEquals(results.size(), 2);
        Assertions.assertNotEquals(results.get(0).getResults(), null);
        Assertions.assertNotEquals(results.get(1).getResults(), null);
        Assertions.assertEquals(results.get(0).getResults().size(), 10);
        Assertions.assertEquals(results.get(1).getResults().size(), 10);
    }

    @Test
    public void bothBadEnginesTest() {
        List<SearchResults> results = new SearchAggregator(BAD_GOOGLE_STUB, BAD_YANDEX_STUB)
                .aggregate("test 3", 10, 1000);
        Assertions.assertEquals(results.size(), 1);
        Assertions.assertTrue(results.get(0).receiveTimeoutExceeded());
    }

    @Test
    public void oneBadEngineTest() {
        List<SearchResults> results = new SearchAggregator(FAST_GOOGLE_STUB, BAD_YANDEX_STUB)
                .aggregate("test 4", 5, 1000);
        Assertions.assertEquals(results.size(), 2);
        Assertions.assertTrue(results.stream().anyMatch(SearchResults::receiveTimeoutExceeded));
        Assertions.assertTrue(results.stream().anyMatch(r -> r.getResults() != null));
    }

    @Test
    public void badQueryTest() {
        List<SearchResults> results = new SearchAggregator(GOOGLE_STUB, YANDEX_STUB)
                .aggregate("test 5 with bad query (because of brackets)", 5, 1000);
        Assertions.assertEquals(results.size(), 2);
        Assertions.assertNotEquals(results.get(0).getException(), null);
        Assertions.assertNotEquals(results.get(1).getException(), null);
    }

    @Test
    public void badResultsNumTest() {
        List<SearchResults> results = new SearchAggregator(GOOGLE_STUB, YANDEX_STUB)
                .aggregate("test 6 with bad results number", -5, 1000);
        Assertions.assertEquals(results.size(), 2);
        Assertions.assertNotEquals(results.get(0).getException(), null);
        Assertions.assertNotEquals(results.get(1).getException(), null);
    }

}
