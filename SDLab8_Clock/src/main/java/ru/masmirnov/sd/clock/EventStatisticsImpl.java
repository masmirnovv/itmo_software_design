package ru.masmirnov.sd.clock;

import ru.masmirnov.sd.clock.clock.*;

import java.time.Instant;
import java.util.*;

public class EventStatisticsImpl extends EventStatistics {

    private static final long CLEAR_INTERVAL_SECONDS = 900;     // 15 minutes
    private static final long LIFETIME_SECONDS = 3600;          // 1 hour

    private Map<String, List<Instant>> timestamps;
    private Clock clock;
    private Instant lastClearTime;


    public EventStatisticsImpl(Clock clock) {
        timestamps = new HashMap<>();
        this.clock = clock;
        lastClearTime = clock.now();
    }

    @Override
    void incEvent(String name) {
        clear();
        if (!timestamps.containsKey(name)) {
            timestamps.put(name, new ArrayList<>());
        }
        timestamps.get(name).add(clock.now());
    }

    @Override
    double getEventStatisticsByName(String name) {
        clear();
        int count = 0;
        Instant now = clock.now();
        List<Instant> times = timestamps.get(name);
        if (times == null)
            return 0;
        for (Instant time : times) {
            if (now.isBefore(time.plusSeconds(LIFETIME_SECONDS))) {
                count++;
            }
        }
        return (double) count / 60;
    }

    @Override
    Map<String, Double> getAllEventsStatistics() {
        Map<String, Double> stats = new HashMap<>();
        for (String event : timestamps.keySet()) {
            stats.put(event, getEventStatisticsByName(event));
        }
        return stats;
    }

    private void clear() {
        if (clock.now().isAfter(lastClearTime.plusSeconds(CLEAR_INTERVAL_SECONDS))) {
            lastClearTime = clock.now();

            Set<String> eventsToDelete = new HashSet<>();
            for (String event : timestamps.keySet()) {
                for (int i = 0; i < timestamps.get(event).size(); i++) {
                    Instant eventTime = timestamps.get(event).get(i);
                    if (clock.now().isAfter(eventTime.plusSeconds(LIFETIME_SECONDS))) {
                        timestamps.get(event).remove(i--);
                    }
                }
                if (timestamps.get(event).isEmpty()) {
                    eventsToDelete.add(event);
                }
            }

            for (String event : eventsToDelete) {
                timestamps.remove(event);
            }
        }
    }

}
