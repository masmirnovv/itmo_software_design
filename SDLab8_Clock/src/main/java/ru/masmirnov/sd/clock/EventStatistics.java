package ru.masmirnov.sd.clock;

import java.util.Map;

public abstract class EventStatistics {

    abstract void incEvent(String name);

    abstract double getEventStatisticsByName(String name);

    abstract Map<String, Double> getAllEventsStatistics();

    public void printStatistics() {
        StringBuilder sb = new StringBuilder("Event statistics:\n");
        for (Map.Entry<String, Double> event : getAllEventsStatistics().entrySet()) {
            sb.append('\t').append(event.getKey()).append(":  RPM = ")
                    .append(event.getValue()).append('\n');
        }
        System.out.println(sb);
    };

}
