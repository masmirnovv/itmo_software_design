package ru.masmirnov.sd.clock;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.masmirnov.sd.clock.clock.NormalClock;
import ru.masmirnov.sd.clock.clock.SetableClock;

import java.util.Map;

public class EventStatisticsTest {

    @Test
    public void allEventsCountTest() {
        EventStatistics es = new EventStatisticsImpl(new NormalClock());
        for (int i = 0; i < 5400; i++) {
            if (i < 1800) es.incEvent("ev-1");
            if (i < 3600) es.incEvent("ev-2");
            es.incEvent("ev-3");
        }
        Map<String, Double> stat = es.getAllEventsStatistics();
        assertAlmostEquals(stat.getOrDefault("ev-1", 0d), 30);
        assertAlmostEquals(stat.getOrDefault("ev-2", 0d), 60);
        assertAlmostEquals(stat.getOrDefault("ev-3", 0d), 90);

        System.out.println("---=== Statistics for test allEventsCountTest ===---");
        es.printStatistics();
    }

    @Test
    public void eventsCountTest() {
        EventStatistics es = new EventStatisticsImpl(new NormalClock());
        for (int i = 0; i < 5400; i++) {
            if (i < 1800) es.incEvent("ev-1");
            if (i < 3600) es.incEvent("ev-2");
            es.incEvent("ev-3");
        }
        assertAlmostEquals(es.getEventStatisticsByName("ev-undef"), 0);
        assertAlmostEquals(es.getEventStatisticsByName("ev-1"), 30);
        assertAlmostEquals(es.getEventStatisticsByName("ev-2"), 60);
        assertAlmostEquals(es.getEventStatisticsByName("ev-3"), 90);

        System.out.println("---=== Statistics for test eventsCountTest ===---");
        es.printStatistics();
    }

    @Test
    public void clockTest() {
        SetableClock clock = new SetableClock();
        EventStatistics es = new EventStatisticsImpl(clock);
        clock.plusMs(50);
        final int n = 2 * 60 * 60 * 1000 / 100;  // 2 hours/100 ms
        for (int i = 0; i < n; i++) {
            es.incEvent("ev");
            if (i != n - 1) {
                clock.plusMs(100);
            }
        }
        clock.plusMs(50);

        assertAlmostEquals(es.getEventStatisticsByName("ev"), 600); // once in 100 ms = 10 times/sec = 600 times/min
        System.out.println("---=== Statistics for test clockTest ===---");
        es.printStatistics();
    }

    private void assertAlmostEquals(double d1, double d2) {
        Assertions.assertTrue(Math.abs(d1 - d2) < 1e-6);
    }

}
