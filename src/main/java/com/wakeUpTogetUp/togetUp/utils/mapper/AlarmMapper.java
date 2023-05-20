package com.wakeUpTogetUp.togetUp.utils.mapper;

import com.wakeUpTogetUp.togetUp.alarms.dto.response.AlarmRes;
import com.wakeUpTogetUp.togetUp.alarms.model.Alarm;
import com.wakeUpTogetUp.togetUp.alarms.dto.response.AlarmsRes;
import com.wakeUpTogetUp.togetUp.routines.dto.response.RoutineRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AlarmMapper {
    AlarmMapper INSTANCE = Mappers.getMapper(AlarmMapper.class);

    // AlarmsRes
    @Mapping(target = "userId", expression = "java(alarm.getUser().getId())")
    AlarmsRes toAlarmsRes(Alarm alarm);

    /**
     *  Entity to DTO
     */
    // AlarmRes
    @Mapping(target = "userId", expression = "java(alarm.getUser().getId())")
    default AlarmRes toAlarmRes(Alarm alarm, List<RoutineRes> routineResList) {
        AlarmRes alarmRes = AlarmRes.builder()
                .id(alarm.getId())
                .userId(alarm.getUser().getId())
                .missionId(alarm.getMission().getId())
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

        alarmRes.setRoutineResList(routineResList);

        return alarmRes;
    }
}
