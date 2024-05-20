package com.wakeUpTogetUp.togetUp.utils.mapper;

import com.wakeUpTogetUp.togetUp.api.alarm.dto.response.AlarmSimpleRes;
import com.wakeUpTogetUp.togetUp.api.alarm.dto.response.GetAlarmRes;
import com.wakeUpTogetUp.togetUp.api.alarm.model.Alarm;
import com.wakeUpTogetUp.togetUp.api.avatar.domain.Avatar;
import com.wakeUpTogetUp.togetUp.api.avatar.domain.AvatarSpeech;
import com.wakeUpTogetUp.togetUp.api.avatar.domain.UserAvatar;
import com.wakeUpTogetUp.togetUp.api.avatar.dto.response.AvatarSpeechResponse;
import com.wakeUpTogetUp.togetUp.api.avatar.dto.response.UserAvatarResponse;
import com.wakeUpTogetUp.togetUp.api.mission.dto.response.GetMissionLogRes;
import com.wakeUpTogetUp.togetUp.api.mission.dto.response.GetMissionObjectRes;
import com.wakeUpTogetUp.togetUp.api.mission.dto.response.GetMissionWithObjectListRes;
import com.wakeUpTogetUp.togetUp.api.mission.model.Mission;
import com.wakeUpTogetUp.togetUp.api.mission.model.MissionLog;
import com.wakeUpTogetUp.togetUp.api.mission.model.MissionObject;
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

    @Mapping(target = "alarmType", expression = "java(alarm.determineAlarmType())")
    @Mapping(target = "missionObject", source = "alarm.missionObject.kr")
    AlarmSimpleRes toAlarmSimpleRes(Alarm alarm);

    List<AlarmSimpleRes> toAlarmSimpleResList(List<Alarm> alarmList);

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


    // Avatar
    @Mapping(source = "avatar.id", target = "avatarId")
    UserAvatarResponse toUserAvatarData(Avatar avatar, @Context List<UserAvatar> userAvatarList);

    List<UserAvatarResponse> toUserAvatarDataList(List<Avatar> avatarList, @Context List<UserAvatar> userAvatarList);

    @AfterMapping
    default void setIsUnlockedStatus(
            @MappingTarget UserAvatarResponse target,
            Avatar avatar,
            @Context List<UserAvatar> userAvatarList) {
        target.setIsUnlocked(
                userAvatarList.stream()
                        .anyMatch(userAvatar -> userAvatar.getAvatar().getId().equals(avatar.getId())));
    }

    AvatarSpeechResponse toAvatarSpeechResponse(AvatarSpeech avatarSpeech);
}
