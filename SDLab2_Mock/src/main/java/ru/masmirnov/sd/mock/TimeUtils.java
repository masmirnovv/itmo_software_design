package ru.masmirnov.sd.mock;

public abstract class TimeUtils {

    public static final long S = 1;
    public static final long MIN = 60 * S;
    public static final long H = 60 * MIN;

    public static long getUnixTime() {
        return System.currentTimeMillis() / 1000L;
    }

    public static long getUnixTimeAgo(long ago) {
        return getUnixTime() - ago;
    }

}
