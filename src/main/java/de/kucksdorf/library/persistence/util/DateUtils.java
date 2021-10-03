package de.kucksdorf.library.persistence.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

public class DateUtils {
    public static LocalDate toDate(Date date) {
        return Optional.ofNullable(date)
                .map(it -> it.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate())
                .orElse(null);
    }
}
