package com.wakeUpTogetUp.togetUp.utils.mappers;

import com.wakeUpTogetUp.togetUp.alarms.model.Alarm;
import com.wakeUpTogetUp.togetUp.alarms.model.GetAlarmRes;
import com.wakeUpTogetUp.togetUp.alarms.model.GetAlarmsRes;
import com.wakeUpTogetUp.togetUp.routines.model.GetRoutineRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AlarmMapper {
    AlarmMapper INSTANCE = Mappers.getMapper(AlarmMapper.class);

    // 알람 한 개 가져오기
    @Mapping(target = "userId", expression = "java(alarm.getUser().getId())")
    default GetAlarmRes entityToGetAlarmRes(Alarm alarm, List<GetRoutineRes> getRoutineResList) {
        GetAlarmRes getAlarmRes = GetAlarmRes.builder()
                .id(alarm.getId())
                .userId(alarm.getUser().getId())
                .name(alarm.getName())
                .icon(alarm.getIcon())
                .sound(alarm.getSound())
                .volume(alarm.getVolume())
                .isVibrate(alarm.getIsVibrate())
                .isRoutineOn(alarm.getIsRoutineOn())
                .snoozeInterval(alarm.getSnoozeInterval())
                .snoozeCnt(alarm.getSnoozeCnt())
                .startHour(alarm.getStartHour())
                .startMinute(alarm.getStartMinute())
                .monday(alarm.getMonday())
                .tuesday(alarm.getTuesday())
                .wednesday(alarm.getWednesday())
                .thursday(alarm.getThursday())
                .friday(alarm.getFriday())
                .saturday(alarm.getSaturday())
                .sunday(alarm.getSunday())
                .isActivated(alarm.getIsActivated())
                .build();

        getAlarmRes.setGetRoutineResList(getRoutineResList);

        return getAlarmRes;
    }

    // 알람 여러 개 가져오기
    @Mapping(target = "userId", expression = "java(alarm.getUser().getId())")
    GetAlarmsRes entityToGetAlarmsRes(Alarm alarm);
}
