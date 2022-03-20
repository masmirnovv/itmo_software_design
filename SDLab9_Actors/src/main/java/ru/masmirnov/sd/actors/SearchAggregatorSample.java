package ru.masmirnov.sd.actors;

import ru.masmirnov.sd.actors.aggregator.SearchAggregator;
import ru.masmirnov.sd.actors.search.SearchEngine;
import ru.masmirnov.sd.actors.search.StubGoogleSearchEngine;
import ru.masmirnov.sd.actors.search.StubYandexSearchEngine;

public class SearchAggregatorSample {

    private static final SearchEngine FAST_GOOGLE_STUB = new StubGoogleSearchEngine(0, 0);
    private static final SearchEngine GOOGLE_STUB = new StubGoogleSearchEngine(100, 200);
    private static final SearchEngine BAD_GOOGLE_STUB = new StubGoogleSearchEngine(Integer.MAX_VALUE, Integer.MAX_VALUE);

    private static final SearchEngine FAST_YANDEX_STUB = new StubYandexSearchEngine(0, 0);
    private static final SearchEngine YANDEX_STUB = new StubYandexSearchEngine(100, 200);
    private static final SearchEngine BAD_YANDEX_STUB = new StubYandexSearchEngine(Integer.MAX_VALUE, Integer.MAX_VALUE);

    public static void main(String[] args) {
        System.out.println("[1/4]  Fast engine stubs\n");
        new SearchAggregator(FAST_GOOGLE_STUB, FAST_YANDEX_STUB)
                .aggregate("first test query", 5, 1000)
                .forEach(System.out::println);

        System.out.println("[2/4]  Average engine stubs\n");
        new SearchAggregator(GOOGLE_STUB, YANDEX_STUB)
                .aggregate("second test query", 5, 1000)
                .forEach(System.out::println);

        System.out.println("[3/4]  Bad engine stubs\n");
        new SearchAggregator(BAD_GOOGLE_STUB, BAD_YANDEX_STUB)
                .aggregate("third test query", 5, 1000)
                .forEach(System.out::println);

        System.out.println("[4/4]  Bad query\n");
        new SearchAggregator(FAST_GOOGLE_STUB, FAST_YANDEX_STUB)
                .aggregate("bad query (because of brackets)", 5, 1000)
                .forEach(System.out::println);
    }

}
