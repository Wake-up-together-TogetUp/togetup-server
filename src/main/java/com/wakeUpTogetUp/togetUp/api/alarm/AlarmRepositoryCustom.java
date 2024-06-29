package com.wakeUpTogetUp.togetUp.api.alarm;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.wakeUpTogetUp.togetUp.api.alarm.dto.response.AlarmSimpleRes;

public interface AlarmRepositoryCustom {

    List<AlarmSimpleRes> getTimeLine(Integer userId, LocalDate today, DayOfWeek dayOfWeek, LocalTime now);
}