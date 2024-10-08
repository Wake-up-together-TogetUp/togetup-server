package com.wakeUpTogetUp.togetUp.api.alarm.repository;

import com.wakeUpTogetUp.togetUp.api.alarm.controller.dto.response.AlarmSimpleRes;
import com.wakeUpTogetUp.togetUp.api.alarm.domain.AlarmType;
import com.wakeUpTogetUp.togetUp.api.alarm.model.Alarm;
import java.time.LocalDateTime;
import java.util.List;

public interface AlarmQueryRepository {

    List<AlarmSimpleRes> findAllUserTodayActiveAlarmsAfterNow(Integer userId, LocalDateTime now);

    List<Alarm> findUserAlarmsByType(Integer userId, AlarmType type);
}