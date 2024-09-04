package com.wakeUpTogetUp.togetUp.utils;

import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateTimeProvider {
    public static final String DEFAULT_TIMEZONE = "Asia/Seoul";

    public static LocalDateTime getCurrentDateTime() {
        ZoneId zoneId = getZoneIdByString(DEFAULT_TIMEZONE);

        return ZonedDateTime.now(zoneId)
                .toLocalDateTime();
    }

    public static LocalDateTime getCurrentDateTime(String timezone) {
        ZoneId zoneId = getZoneIdByString(timezone);

        return ZonedDateTime.now(zoneId)
                .toLocalDateTime();
    }

    public static String getDateTimeByFormat(String format) {
        ZoneId zoneId = getZoneIdByString(DEFAULT_TIMEZONE);

        return ZonedDateTime.now(zoneId)
                .format(DateTimeFormatter
                        .ofPattern(format)
                        .withLocale(Locale.ROOT));
    }

    public static String getDateTimeByFormat(String timezone, String format) {
        ZoneId zoneId = getZoneIdByString(timezone);

        return ZonedDateTime.now(zoneId)
                .format(DateTimeFormatter
                        .ofPattern(format)
                        .withLocale(Locale.ROOT));
    }

    private static ZoneId getZoneIdByString(String zoneId) {
        try {
            return ZoneId.of(zoneId);
        } catch (DateTimeException e) {
            throw new BaseException(Status.TIME_ZONE_ID_NOT_EXIST);
        }
    }
}
