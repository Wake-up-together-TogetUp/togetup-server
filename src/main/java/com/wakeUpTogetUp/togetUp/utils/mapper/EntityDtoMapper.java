package com.wakeUpTogetUp.togetUp.utils.mapper;

import com.wakeUpTogetUp.togetUp.api.alarm.dto.response.AlarmSimpleRes;
import com.wakeUpTogetUp.togetUp.api.alarm.dto.response.GetAlarmRes;
import com.wakeUpTogetUp.togetUp.api.alarm.model.Alarm;
import com.wakeUpTogetUp.togetUp.api.avatar.model.Avatar;
import com.wakeUpTogetUp.togetUp.api.avatar.vo.UserAvatarData;
import com.wakeUpTogetUp.togetUp.api.mission.dto.response.GetMissionLogRes;
import com.wakeUpTogetUp.togetUp.api.mission.dto.response.GetMissionObjectRes;
import com.wakeUpTogetUp.togetUp.api.mission.dto.response.GetMissionWithObjectListRes;
import com.wakeUpTogetUp.togetUp.api.mission.model.Mission;
import com.wakeUpTogetUp.togetUp.api.mission.model.MissionLog;
import com.wakeUpTogetUp.togetUp.api.mission.model.MissionObject;
import com.wakeUpTogetUp.togetUp.api.notification.dto.response.NotificationRes;
import com.wakeUpTogetUp.togetUp.api.notification.entity.Notification;
import com.wakeUpTogetUp.togetUp.api.room.dto.response.RoomDetailRes;
import com.wakeUpTogetUp.togetUp.api.room.dto.response.RoomInfoRes;
import com.wakeUpTogetUp.togetUp.api.room.dto.response.RoomRes;
import com.wakeUpTogetUp.togetUp.api.room.dto.response.RoomUserMissionLogRes;
import com.wakeUpTogetUp.togetUp.api.room.model.RoomUser;
import com.wakeUpTogetUp.togetUp.api.users.model.UserAvatar;
import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EntityDtoMapper {

    EntityDtoMapper INSTANCE = Mappers.getMapper(EntityDtoMapper.class);

    // Alarm
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "getMissionRes", source = "mission")
    @Mapping(target = "getMissionObjectRes", source = "missionObject")
    @Mapping(target = "roomRes", source = "room")
    @Mapping(target = "roomRes.roomId", source = "room.id")
    @Mapping(target = "roomRes.icon", source = "icon")
    GetAlarmRes toAlarmRes(Alarm alarm);

    List<GetAlarmRes> toAlarmResList(List<Alarm> alarmList);

    @Mapping(target = "missionObject", source = "alarm.missionObject.name")
    AlarmSimpleRes toAlarmSimpleRes(Alarm alarm);
    
    // Mission
    GetMissionObjectRes toGetMissionObjectRes(MissionObject missionObject);

    List<GetMissionObjectRes> toGetMissionObjectResList(List<MissionObject> missionObjectList);

    @Mapping(target = "missionObjectResList", source = "mission.missionObjectList")
    GetMissionWithObjectListRes toGetMissionRes(Mission mission);

    List<GetMissionWithObjectListRes> toGetMissionResList(List<Mission> missionList);


    // GetMissionLogRes
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "roomId", source = "room.id")
    GetMissionLogRes toMissionLogRes(MissionLog missionLog);

    List<GetMissionLogRes> toMissionLogResList(List<MissionLog> missionLogList);

    // notification
    @Mapping(target = "roomId", source = "room.id")
    NotificationRes toNotificationRes(Notification notification);

    List<NotificationRes> toNotificationResList(List<Notification> notificationList);


    //Room
    @Mapping(target = "roomId", source = "room.id")
    @Mapping(target = "icon", source = "icon")
    @Mapping(target = "name", source = "room.name")
    @Mapping(target = "mission", source = "missionObject.name")
    @Mapping(target = "kr", source = "missionObject.kr")
    RoomRes toRoomRes(Alarm alarm);

    List<RoomRes> toRoomResList(List<Alarm> alarmList);


    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userName", source = "user.name")
    RoomUserMissionLogRes.UserLogData toUserLogData(RoomUser roomUser);

    List<RoomUserMissionLogRes.UserLogData> toUserLogDataList(List<RoomUser> roomUser);

    @Mapping(target = "icon", source = "icon")
    @Mapping(target = "name", source = "room.name")
    @Mapping(target = "intro", source = "room.intro")
    @Mapping(target = "invitationCode", source = "room.invitationCode")
    RoomDetailRes.RoomData toRoomDetailResRoomData(Alarm alarm);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "missionKr", source = "missionObject.kr")
    RoomDetailRes.AlarmData toRoomDetailResAlarmData(Alarm alarm);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userName", source = "user.name")
    @Mapping(target = "isHost", source = "isHost")
    @Mapping(target = "level", source = "user.level")
    RoomDetailRes.UserData toRoomDetailUserData(RoomUser roomUser);

    List<RoomDetailRes.UserData> toUserDataList(List<RoomUser> roomUser);


    @Mapping(target = "id", source = "room.id")
    @Mapping(target = "icon", source = "icon")
    @Mapping(target = "name", source = "room.name")
    @Mapping(target = "intro", source = "room.intro")
    RoomInfoRes.RoomData toRoomInfoResRoomData(Alarm alarm);


    @Mapping(target = "missionKr", source = "missionObject.kr")
    RoomInfoRes.AlarmData toRoomInfoResAlarmData(Alarm alarm);

    // Avatar
    @Mapping(source = "avatar.id", target = "avatarId")
    @Mapping(source = "avatar.theme.value", target = "theme")
    UserAvatarData toUserAvatarData(Avatar avatar, @Context List<UserAvatar> userAvatarList);

    List<UserAvatarData> toUserAvatarDataList(List<Avatar> avatarList,
                                              @Context List<UserAvatar> userAvatarList);

    @AfterMapping
    default void setIsUnlockedStatus(@MappingTarget UserAvatarData target, Avatar avatar,
                                     @Context List<UserAvatar> userAvatarList) {
        target.setIsUnlocked(userAvatarList.stream()
                .anyMatch(userAvatar -> userAvatar.getAvatar().getId().equals(avatar.getId())));
    }

}
