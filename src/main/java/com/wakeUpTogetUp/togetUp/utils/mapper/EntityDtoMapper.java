package com.wakeUpTogetUp.togetUp.utils.mapper;

import com.wakeUpTogetUp.togetUp.alarm.dto.response.AlarmRes;
import com.wakeUpTogetUp.togetUp.alarm.dto.response.AlarmsRes;
import com.wakeUpTogetUp.togetUp.alarm.model.Alarm;
import com.wakeUpTogetUp.togetUp.fcmNotification.dto.response.PushLogRes;
import com.wakeUpTogetUp.togetUp.fcmNotification.entity.PushLog;
import com.wakeUpTogetUp.togetUp.mission.dto.response.GetMissionRes;
import com.wakeUpTogetUp.togetUp.mission.dto.response.MissionCompleteLogRes;
import com.wakeUpTogetUp.togetUp.mission.model.Mission;
import com.wakeUpTogetUp.togetUp.mission.model.MissionCompleteLog;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@org.mapstruct.Mapper(componentModel = "spring")
public interface EntityDtoMapper {
    EntityDtoMapper INSTANCE = Mappers.getMapper(EntityDtoMapper.class);

    // Alarm
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "missionId", source = "mission.id")
    AlarmsRes toAlarmsRes(Alarm alarm);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "missionId", source = "mission.id")
    AlarmRes toAlarmRes(Alarm alarm);

    // Mission
    GetMissionRes toGetMissionRes(Mission mission);
    List<GetMissionRes> toGetMissionResList(List<Mission> missionList);




    // MissionCompleteLog
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "alarmId", source = "alarm.id")
    MissionCompleteLogRes toMissionCompleteLogRes(MissionCompleteLog missionCompleteLog);
    List<MissionCompleteLogRes> toMissionCompleteLogResList(List<MissionCompleteLog> missionCompleteLogList);

    // PushLog
    @Mapping(target = "receiverId", source = "receiver.id")
    PushLogRes toPushLogRes(PushLog pushLog);
    List<PushLogRes> toPushLogResList(List<PushLog> pushLogList);
}
