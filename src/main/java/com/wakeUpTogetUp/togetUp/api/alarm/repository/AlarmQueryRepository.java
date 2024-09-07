package com.wakeUpTogetUp.togetUp.api.alarm.repository;

import com.wakeUpTogetUp.togetUp.api.alarm.controller.dto.response.AlarmSimpleRes;
import java.time.LocalDateTime;
import java.util.List;

public interface AlarmQueryRepository {

    List<AlarmSimpleRes> findAllUserTodayActiveAlarmsAfterNow(Integer userId, LocalDateTime now);
}