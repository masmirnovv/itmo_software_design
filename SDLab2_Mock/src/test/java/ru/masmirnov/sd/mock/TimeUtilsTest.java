package ru.masmirnov.sd.mock;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TimeUtilsTest {

    @Test
    public void threadWaitTest() {
        long timestamp1 = TimeUtils.getUnixTime();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) { }
        long timestamp2 = TimeUtils.getUnixTime();
        assertAlmostEquals(timestamp2 - timestamp1, 1);
    }

    @Test
    public void timestampAgoTest() {
        long ts    = TimeUtils.getUnixTime();
        long tsS   = TimeUtils.getUnixTimeAgo(5 * TimeUtils.S);
        long tsMin = TimeUtils.getUnixTimeAgo(5 * TimeUtils.MIN);
        long tsH   = TimeUtils.getUnixTimeAgo(5 * TimeUtils.H);
        assertAlmostEquals(ts - tsS, 5 * TimeUtils.S);
        assertAlmostEquals(tsS - tsMin, 295 * TimeUtils.S);
        assertAlmostEquals(tsMin - tsH, 295 * TimeUtils.MIN);
    }

    private static void assertAlmostEquals(long l1, long l2) {
        Assertions.assertTrue(Math.abs(l1 - l2) <= 1);
    }

}
