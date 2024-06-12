package com.wakeUpTogetUp.togetUp.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.springframework.stereotype.Component;

@Component
public class DateTimeProvider {

    private final static ZoneId SEOUL_ZONE_ID = ZoneId.of("Asia/Seoul");

    public static LocalDate getCurrentDateInSeoul() {
        return ZonedDateTime.now(SEOUL_ZONE_ID).toLocalDate();
    }

    public static LocalDateTime getCurrentDateTimeInSeoul() {
        return ZonedDateTime.now(SEOUL_ZONE_ID).toLocalDateTime();
    }

    public static String getDateTimeByFormat(String format) {
        return ZonedDateTime.now(SEOUL_ZONE_ID)
                .format(
                        DateTimeFormatter
                                .ofPattern(format)
                                .withLocale(Locale.ROOT));
    }
}
