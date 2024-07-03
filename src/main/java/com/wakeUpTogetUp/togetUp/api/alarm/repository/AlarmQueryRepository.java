package com.wakeUpTogetUp.togetUp.api.alarm.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.wakeUpTogetUp.togetUp.api.alarm.dto.response.AlarmSimpleRes;

public interface AlarmQueryRepository {

    List<AlarmSimpleRes> findAllUserTodayActiveAlarmsAfterNow(Integer userId, LocalDateTime now);
}