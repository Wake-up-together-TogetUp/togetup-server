package com.wakeUpTogetUp.togetUp.utils.mapper;

import com.wakeUpTogetUp.togetUp.api.alarm.dto.request.AlarmCreateReq;
import com.wakeUpTogetUp.togetUp.api.alarm.dto.request.PostAlarmReq;
import org.springframework.stereotype.Component;
@Component
public class AlarmMigrationMapper {

    public PostAlarmReq convertAlarmCreateReqToPostAlarmReq(AlarmCreateReq alarmCreateReq){
        return PostAlarmReq.of(
                alarmCreateReq.getName(),
                "⏰",
                5,
                3,
                alarmCreateReq.getAlarmTime(),
                alarmCreateReq.isMonday(),
                alarmCreateReq.isTuesday(),
                alarmCreateReq.isWednesday(),
                alarmCreateReq.isThursday(),
                alarmCreateReq.isFriday(),
                alarmCreateReq.isSaturday(),
                alarmCreateReq.isSunday(),
                true,
                alarmCreateReq.getIsVibrate(),
                alarmCreateReq.getMissionId(),
                alarmCreateReq.getMissionObjectId()
        );
    }

}
