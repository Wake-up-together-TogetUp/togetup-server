package com.wakeUpTogetUp.togetUp.utils;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import org.springframework.stereotype.Component;


@Component
public class TimeFormatter {
    public static SimpleDateFormat simpleDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String timestampFormat(Timestamp timestamp) {
        return simpleDateTimeFormat.format(timestamp);
    }




}
