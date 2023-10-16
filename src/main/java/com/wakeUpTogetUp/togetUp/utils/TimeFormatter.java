package com.wakeUpTogetUp.togetUp.utils;


import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Stream;

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
    //yyyy-MM-dd HH:mm:ss
    public String timestampToDotDateFormat(Timestamp timestamp){

        String formattedDate = simpleDotDateFormat.format(timestamp);

        return  formattedDate;
    }

    public LocalDateTime stringToLocalDateTime(String timeString)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(timeString, formatter);
        return localDateTime;

    }

    public String timeStringToAMPMFormat(String timeString) {
        LocalTime localTime = stringToLocalTime(timeString);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("a h:mm").withLocale(Locale.ENGLISH);
        return localTime.format(formatter).toLowerCase();
    }




    public String formatDaysOfWeek(boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday, boolean saturday, boolean sunday) {

        StringBuilder formattedDays = new StringBuilder();

        Boolean[] weekDays= {true, true, true, true, true, false, false };
        Boolean[] weekend = {false, false, false, false, false, true, true};
        Boolean[] none = {false, false, false, false, false, false, false};
        Boolean[] everyDay= {true, true, true, true, true, true, true };


        Boolean[] alarmDays = {monday, tuesday, wednesday, thursday, friday, saturday, sunday };

        if(Objects.deepEquals(weekDays,alarmDays))
            formattedDays.append("주중");
        else if(Objects.deepEquals(weekend,alarmDays))
            formattedDays.append("주말");
        else if (Objects.deepEquals(none,alarmDays))
            formattedDays.append("");
        else if(Objects.deepEquals(everyDay,alarmDays))
            formattedDays.append("매일");
        else
        {

            for (int i = 0; i < alarmDays.length; i++) {
                if (alarmDays[i]) {
                    formattedDays.append(new String[]{"월", "화", "수", "목", "금","토","일"}[i]);
                    formattedDays.append(",");
                }
            }
            formattedDays.deleteCharAt(formattedDays.length() - 1);
        }

        return formattedDays.toString();
    }

}
