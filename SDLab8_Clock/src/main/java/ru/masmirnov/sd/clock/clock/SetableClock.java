package ru.masmirnov.sd.clock.clock;

import java.time.Instant;

public class SetableClock implements Clock {

    private Instant now;

    public SetableClock(Instant now) {
        this.now = now;
    }

    public SetableClock() {
        this.now = Instant.now();
    }

    public Instant now() {
        return now;
    }

    public void plusMs(long millis) {
        now = now.plusMillis(millis);
    }

}
