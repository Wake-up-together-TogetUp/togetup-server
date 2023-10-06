package com.wakeUpTogetUp.togetUp.utils;


import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
public class TimeFormatter {
    public static SimpleDateFormat simpleDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat simpleDotDateFormat = new SimpleDateFormat("yyyy.MM.dd");
    public static String timestampFormat(Timestamp timestamp) {
        return simpleDateTimeFormat.format(timestamp);
    }


    public LocalTime stringToLocalTime(String timeString)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime localTime = LocalTime.parse(timeString, formatter);
        return localTime;

    }
    public String timestampToDotDateFormat(Timestamp timestamp){

        String formattedDate = simpleDotDateFormat.format(timestamp);

        return  formattedDate;
    }
}
