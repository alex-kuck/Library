package de.kucksdorf.library.utils;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;

public class DateUtils {
    private static Clock CLOCK = defaultClock();

    private static Clock defaultClock() {
        return Clock.systemDefaultZone();
    }

    public static LocalDate currentDate() {
        return LocalDate.now(CLOCK);
    }

    public static void setClock(LocalDate date) {
        CLOCK = Clock.fixed(date.atStartOfDay(ZoneId.systemDefault())
                .toInstant(), ZoneId.systemDefault());
    }

    public static void useDefaultClock() {
        CLOCK = defaultClock();
    }

    public static boolean isActiveRange(LocalDate start, LocalDate end) {
        return isContained(currentDate(), start, end);
    }

    public static boolean isContained(LocalDate date, LocalDate start, LocalDate end) {
        boolean isActive = true;
        if (start == null) {
            isActive = false;
        } else {
            if (start.isAfter(date)) {
                isActive = false;
            } else if (end != null && end.isBefore(date)) {
                isActive = false;
            }
        }
        return isActive;
    }
}
