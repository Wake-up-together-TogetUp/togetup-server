package com.wakeUpTogetUp.togetUp.utils.mapper;

import com.wakeUpTogetUp.togetUp.api.alarm.dto.response.GetAlarmRes;
import com.wakeUpTogetUp.togetUp.api.alarm.model.Alarm;
import com.wakeUpTogetUp.togetUp.api.mission.dto.response.GetMissionObjectRes;
import com.wakeUpTogetUp.togetUp.api.mission.model.MissionObject;
import com.wakeUpTogetUp.togetUp.api.notification.dto.response.NotificationRes;
import com.wakeUpTogetUp.togetUp.api.notification.entity.Notification;
import com.wakeUpTogetUp.togetUp.api.mission.dto.response.GetMissionWithObjectListRes;
import com.wakeUpTogetUp.togetUp.api.mission.dto.response.GetMissionLogRes;
import com.wakeUpTogetUp.togetUp.api.mission.model.Mission;
import com.wakeUpTogetUp.togetUp.api.mission.model.MissionLog;
import com.wakeUpTogetUp.togetUp.api.room.dto.response.RoomDetailRes;
import com.wakeUpTogetUp.togetUp.api.room.dto.response.RoomUserMissionLogRes;
import com.wakeUpTogetUp.togetUp.api.room.dto.response.RoomRes;
import com.wakeUpTogetUp.togetUp.api.room.model.RoomUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EntityDtoMapper {
    EntityDtoMapper INSTANCE = Mappers.getMapper(EntityDtoMapper.class);

    // Alarm
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "getMissionRes", source = "mission")
    @Mapping(target = "getMissionObjectRes", source = "missionObject")
    @Mapping(target = "roomRes", source = "room")
    GetAlarmRes toAlarmRes(Alarm alarm);

    List<GetAlarmRes> toAlarmResList(List<Alarm> alarmList);


    // Mission
    GetMissionObjectRes toGetMissionObjectRes(MissionObject missionObject);
    List<GetMissionObjectRes> toGetMissionObjectResList(List<MissionObject> missionObjectList);

    @Mapping(target = "missionObjectResList", source = "mission.missionObjectList")
    GetMissionWithObjectListRes toGetMissionRes(Mission mission);
    List<GetMissionWithObjectListRes> toGetMissionResList(List<Mission> missionList);


    // MissionCompleteLog
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "roomId", source = "room.id")
    @Mapping(target = "missionId", source = "mission.id")
    GetMissionLogRes toMissionLogRes(MissionLog missionLog);
    List<GetMissionLogRes> toMissionLogResList(List<MissionLog> missionLogList);

    // notification
    @Mapping(target = "roomId", source = "room.id")
    NotificationRes toNotificationRes(Notification notification);
    List<NotificationRes> toNotificationResList(List<Notification> notificationList);


    @Mapping(target = "roomId",source = "room.id")
    @Mapping(target = "icon",source = "icon")
    @Mapping(target = "name",source = "room.name")
    @Mapping(target = "mission",source = "missionObject.name")
    @Mapping(target = "kr",source = "missionObject.kr")
    RoomRes toRoomRes(Alarm alarm);

    //Room
    @Mapping(target = "id",source = "room")
    @Mapping(target = "icon",source = "icon")
    @Mapping(target = ".",source = "room")
    @Mapping(target = "mission",source = "missionObject.name")
    @Mapping(target = "kr",source = "missionObject.kr")
    List<RoomRes> toRoomResList(List<Alarm> alarmList);


    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userName",source = "user.name")
    RoomUserMissionLogRes.UserLogData toUserLogData(RoomUser roomUser);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userName",source = "user.name")
    List<RoomUserMissionLogRes.UserLogData> toUserLogDataList(List<RoomUser> roomUser);

    @Mapping(target = "icon",source = "icon")
    @Mapping(target = "name",source = "room.name")
    @Mapping(target = "intro",source = "room.intro")
    RoomDetailRes toRoomDetailRes(Alarm alarm);
    @Mapping(target = "userId",source = "user.id")
    @Mapping(target = "userName",source = "user.name")
    @Mapping(target = "isHost",source = "isHost")
    RoomDetailRes.UserData toUserData(RoomUser roomUser);

    @Mapping(target = "userId",source = "user.id")
    @Mapping(target = "userName",source = "user.name")
    @Mapping(target = "isHost",source = "isHost")
    List<RoomDetailRes.UserData> toUserDataList(List<RoomUser> roomUser);



}
