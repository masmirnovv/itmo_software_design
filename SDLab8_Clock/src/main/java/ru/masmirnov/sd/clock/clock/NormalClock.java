package ru.masmirnov.sd.clock.clock;

import java.time.Instant;

public class NormalClock implements Clock {

    public Instant now() {
        return Instant.now();
    }

}
