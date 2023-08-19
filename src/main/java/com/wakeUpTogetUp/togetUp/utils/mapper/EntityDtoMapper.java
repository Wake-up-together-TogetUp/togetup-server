package com.wakeUpTogetUp.togetUp.utils.mapper;

import com.wakeUpTogetUp.togetUp.api.alarm.dto.response.GetAlarmRes;
import com.wakeUpTogetUp.togetUp.api.alarm.model.Alarm;
import com.wakeUpTogetUp.togetUp.api.fcmNotification.dto.response.NotificationRes;
import com.wakeUpTogetUp.togetUp.api.fcmNotification.entity.Notification;
import com.wakeUpTogetUp.togetUp.api.mission.dto.response.GetMissionRes;
import com.wakeUpTogetUp.togetUp.api.mission.dto.response.MissionLogRes;
import com.wakeUpTogetUp.togetUp.api.mission.model.Mission;
import com.wakeUpTogetUp.togetUp.api.mission.model.MissionLog;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@org.mapstruct.Mapper(componentModel = "spring")
public interface EntityDtoMapper {
    EntityDtoMapper INSTANCE = Mappers.getMapper(EntityDtoMapper.class);

    // Alarm
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "missionId", source = "mission.id")
    GetAlarmRes toAlarmRes(Alarm alarm);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "missionId", source = "mission.id")
    List<GetAlarmRes> toAlarmResList(List<Alarm> alarmList);


    // Mission
    GetMissionRes toGetMissionRes(Mission mission);
    List<GetMissionRes> toGetMissionResList(List<Mission> missionList);


    // MissionCompleteLog
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "roomId", source = "room.id")
    @Mapping(target = "missionId", source = "mission.id")
    MissionLogRes toMissionLogRes(MissionLog missionLog);
    List<MissionLogRes> toMissionLogResList(List<MissionLog> missionLogList);

    // PushLog
    NotificationRes toNotificationRes(Notification notification);
    List<NotificationRes> toNotificationResList(List<Notification> notificationList);
}
