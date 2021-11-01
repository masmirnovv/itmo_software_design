package ru.masmirnov.sd.mock.server;

public abstract class ServerUtils {

    private static final String QUERY_VALIDATION_REGEX = "[a-zA-zа-яА-ЯёЁ0-9]+";

    public static boolean isValidQuery(String searchQuery) {
        return searchQuery.matches(QUERY_VALIDATION_REGEX);
    }

    public static void assertValidQuery(String searchQuery) {
        if (!isValidQuery(searchQuery))
            throw new IllegalArgumentException("Invalid search query: " + searchQuery);
    }

    public static boolean isValidHours(int hours) {
        return hours >= 1 && hours <= 24;
    }

    public static void assertValidHours(int hours) {
        if (!isValidHours(hours))
            throw new IllegalArgumentException("Invalid hours number: " + hours + " (expected 1 to 24)");
    }

}
