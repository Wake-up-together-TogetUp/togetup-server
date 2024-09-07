package com.wakeUpTogetUp.togetUp.utils.mapper;

import com.wakeUpTogetUp.togetUp.api.alarm.controller.dto.request.AlarmCreateReq;
import com.wakeUpTogetUp.togetUp.api.alarm.controller.dto.request.PostAlarmReq;
import org.springframework.stereotype.Component;
@Component
public class AlarmMigrationMapper {

    public PostAlarmReq convertAlarmCreateReqToPostAlarmReq(AlarmCreateReq alarmCreateReq){
        return PostAlarmReq.of(
                alarmCreateReq.getName(),
                alarmCreateReq.getAlarmTime(),
                alarmCreateReq.isMonday(),
                alarmCreateReq.isTuesday(),
                alarmCreateReq.isWednesday(),
                alarmCreateReq.isThursday(),
                alarmCreateReq.isFriday(),
                alarmCreateReq.isSaturday(),
                alarmCreateReq.isSunday(),
                alarmCreateReq.getIsVibrate(),
                alarmCreateReq.getMissionId(),
                alarmCreateReq.getMissionObjectId()
        );
    }
}
