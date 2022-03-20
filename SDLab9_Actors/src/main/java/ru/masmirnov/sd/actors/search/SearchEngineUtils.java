package ru.masmirnov.sd.actors.search;

import ru.masmirnov.sd.actors.search.ex.InvalidQueryException;

public abstract class SearchEngineUtils {

    private static boolean isValidChar(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')
                || (c >= '0' && c <= '9') || c == ' ';
    }

    public static void validate(String q) throws InvalidQueryException {
        for (char c : q.toCharArray()) {
            if (!isValidChar(c)) {
                throw new InvalidQueryException("Forbidden char: " + c);
            }
        }
    }

    public static void rndSleep(int millisFrom, int millisTo) {
        try {
            Thread.sleep((int) (Math.random() * (millisTo - millisFrom + 1)) + millisFrom);
        } catch (InterruptedException ignored) { }
    }

}
