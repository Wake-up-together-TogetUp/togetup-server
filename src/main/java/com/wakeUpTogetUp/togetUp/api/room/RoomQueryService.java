package com.wakeUpTogetUp.togetUp.api.room;

import com.wakeUpTogetUp.togetUp.api.alarm.AlarmRepository;
import com.wakeUpTogetUp.togetUp.api.avatar.repository.UserAvatarRepository;
import com.wakeUpTogetUp.togetUp.api.mission.model.MissionLog;
import com.wakeUpTogetUp.togetUp.api.mission.model.MissionObject;
import com.wakeUpTogetUp.togetUp.api.mission.repository.MissionLogRepository;
import com.wakeUpTogetUp.togetUp.api.room.dto.response.RoomDetailRes;
import com.wakeUpTogetUp.togetUp.api.room.dto.response.RoomInviteInfoRes;
import com.wakeUpTogetUp.togetUp.api.room.dto.response.RoomUserMissionLogRes;
import com.wakeUpTogetUp.togetUp.api.room.dto.response.UserLogData;
import com.wakeUpTogetUp.togetUp.api.room.dto.response.UserProfileData;
import com.wakeUpTogetUp.togetUp.api.room.model.Room;
import com.wakeUpTogetUp.togetUp.api.room.model.RoomUser;
import com.wakeUpTogetUp.togetUp.api.users.UserRepository;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class RoomQueryService {

    private final AlarmRepository alarmRepository;
    private final UserAvatarRepository userAvatarRepository;
    private final RoomQueryRepository roomQueryRepository;
    private final UserRepository userRepository;
    private final MissionLogRepository missionLogRepository;


    public RoomUserMissionLogRes getRoomUserLogList(Integer userId, Integer roomId, LocalDate localDate) {

        Room room = roomQueryRepository.findById(roomId)
                .orElseThrow(() -> new BaseException(Status.ROOM_NOT_FOUND));

        String theme = userAvatarRepository.findByUser_IdAndIsActiveIsTrue(userId)
                .orElseThrow(() -> new BaseException(Status.FIND_USER_AVATAR_FAIL))
                .getAvatar()
                .getTheme();

        List<UserLogData> roomUserMissionLogRes = getUserLogData(userId,roomId,localDate);

        return RoomUserMissionLogRes.of(room.getName(), theme, roomUserMissionLogRes);
    }

    public List<UserLogData> getUserLogData(Integer userId, Integer roomId,LocalDate localDate){
        List<RoomUser> roomUsers = roomQueryRepository.findAllByRoomIdOrderByUserIdAndUserName(userId,roomId);
        List<MissionLog> missionLogs = missionLogRepository.findAllByRoomIdAndDate(roomId,localDate);


        List<UserLogData> userLogDataList = roomUsers.stream().map(roomUser -> {
            String missionPicLink = missionLogs.stream()
                    .filter(missionLog -> missionLog.getUser().getId().equals(roomUser.getUser().getId()))
                    .findAny()
                    .map(MissionLog::getMissionPicLink)
                    .orElse("");

            return UserLogData.of(
                    roomUser.getUser().getId(),
                    roomUser.getUser().getName(),
                    missionPicLink
            );
        }).collect(Collectors.toList());

        return userLogDataList;

    }


    public RoomDetailRes getRoomDetail(Integer roomId, Integer userId) {

        RoomDetailRes.RoomData roomData = getRoomData(roomId);
        RoomDetailRes.MissionData missionData = getMissionData(roomId);
        List<UserProfileData> userProfileData = userRepository.findUserProfileDataByRoomIdOrderedByUserIdAndName(userId, roomId);

        return RoomDetailRes.of(
                roomData,
                missionData,
                userProfileData
        );


    }

    public RoomDetailRes.RoomData getRoomData(Integer roomId) {
        Room room = roomQueryRepository.findById(roomId)
                .orElseThrow(() -> new BaseException(Status.ROOM_NOT_FOUND));
        return RoomDetailRes.RoomData.of(
                room.getName(),
                room.getIntro(),
                room.getCreatedAt().toLocalDateTime().toLocalDate(),
                room.getRoomUsers().size(),
                room.getInvitationCode()
        );
    }

    public RoomDetailRes.MissionData getMissionData(Integer roomId){
        MissionObject roomMissionObject = alarmRepository.findMissionObjectByRoomId(roomId);
        return RoomDetailRes.MissionData.of(roomMissionObject.getIcon(), roomMissionObject.getKr());
    }

    public RoomInviteInfoRes getRoomInformation(String invitationCode) {

        Room room = roomQueryRepository.findByInvitationCode(invitationCode)
                .orElseThrow(() -> new BaseException(Status.ROOM_NOT_FOUND));
        MissionObject roomMissionObject = alarmRepository.findMissionObjectByRoomId(room.getId());
        return RoomInviteInfoRes.of(
                room.getId(),
                roomMissionObject.getIcon(),
                room.getName(),
                room.getIntro(),
                room.getCreatedAt(),
                room.getRoomUsers().size(),
                roomMissionObject.getId(),
                roomMissionObject.getKr()
        );
    }


}
