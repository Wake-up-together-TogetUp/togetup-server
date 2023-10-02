package com.wakeUpTogetUp.togetUp.utils;


import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class TimeFormatter {
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static String timestampFormat(Timestamp timestamp) {
        return simpleDateFormat.format(timestamp);
    }


    public LocalTime stringToLocalTime(String timeString)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime localTime = LocalTime.parse(timeString, formatter);
        return localTime;

    }
}
