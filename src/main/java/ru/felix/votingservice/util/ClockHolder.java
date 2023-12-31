package ru.felix.votingservice.util;

import jakarta.annotation.Nonnull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.time.Clock;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@UtilityClass
public class ClockHolder {

//    https://habr.com/ru/articles/681608/
    private static final AtomicReference<Clock> CLOCK_REFERENCE = new AtomicReference<>(Clock.systemDefaultZone());

    @Nonnull
    public static Clock getClock() {
        return CLOCK_REFERENCE.get();
    }

    @Nonnull
    public static Clock setClock(@Nonnull final Clock newClock) {
        Objects.requireNonNull(newClock, "newClock cannot be null");
        final Clock oldClock = CLOCK_REFERENCE.getAndSet(newClock);
        log.info("Set new clock {}. Old clock is {}", newClock, oldClock);
        return oldClock;
    }
}
