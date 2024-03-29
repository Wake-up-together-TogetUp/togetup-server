package com.wakeUpTogetUp.togetUp.api.room;

import com.wakeUpTogetUp.togetUp.api.alarm.AlarmRepository;
import com.wakeUpTogetUp.togetUp.api.alarm.AlarmService;
import com.wakeUpTogetUp.togetUp.api.alarm.dto.request.AlarmCreateReq;
import com.wakeUpTogetUp.togetUp.api.alarm.dto.request.PostAlarmReq;
import com.wakeUpTogetUp.togetUp.api.alarm.model.Alarm;
import com.wakeUpTogetUp.togetUp.api.mission.repository.MissionLogRepository;
import com.wakeUpTogetUp.togetUp.api.mission.model.MissionLog;
import com.wakeUpTogetUp.togetUp.api.mission.model.MissionObject;
import com.wakeUpTogetUp.togetUp.api.room.dto.request.RoomReq;
import com.wakeUpTogetUp.togetUp.api.room.dto.response.RoomDetailRes;
import com.wakeUpTogetUp.togetUp.api.room.dto.response.RoomInfoRes;
import com.wakeUpTogetUp.togetUp.api.room.dto.response.RoomRes;
import com.wakeUpTogetUp.togetUp.api.room.dto.response.RoomUserMissionLogRes;
import com.wakeUpTogetUp.togetUp.api.room.model.Room;
import com.wakeUpTogetUp.togetUp.api.room.model.RoomUser;
import com.wakeUpTogetUp.togetUp.api.users.UserAvatarRepository;
import com.wakeUpTogetUp.togetUp.api.users.UserRepository;
import com.wakeUpTogetUp.togetUp.api.users.model.User;
import com.wakeUpTogetUp.togetUp.api.users.model.UserAvatar;
import com.wakeUpTogetUp.togetUp.common.Constant;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.utils.TimeFormatter;
import com.wakeUpTogetUp.togetUp.utils.mapper.AlarmMigrationMapper;
import com.wakeUpTogetUp.togetUp.utils.mapper.EntityDtoMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.wakeUpTogetUp.togetUp.api.users.UserServiceHelper.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final AlarmService alarmService;
    private final UserRepository userRepository;
    private final RoomUserRepository roomUserRepository;
    private final AlarmRepository alarmRepository;
    private final MissionLogRepository missionLogRepository;
    private final TimeFormatter timeFormatter;
    private final UserAvatarRepository userAvatarRepository;
    private final AlarmMigrationMapper alarmMigrationMapper;

    @Transactional
    public void createRoom(Integer userId, RoomReq roomReq) {

        Room room = Room.builder()
                .name(roomReq.getName())
                .intro(roomReq.getIntro())
                .build();

        PostAlarmReq postAlarmReq = alarmMigrationMapper.convertAlarmCreateReqToPostAlarmReq(roomReq.getAlarmCreateReq());

        Integer alarmId = alarmService.createAlarmDeprecated(userId, postAlarmReq).getId();
        Room savedRoom = roomRepository.save(room);
        this.joinRoom(savedRoom.getId(), userId);

        alarmRepository.updateRoomIdByAlarmId(alarmId, savedRoom.getId());

    }


    public List<RoomRes> getRoomList(Integer userId) {

        List<Integer> roomIdsByUserId = roomUserRepository.findAllRoomIdsByUserId(userId);

        List<Alarm> alarmList = alarmRepository.findAllByRoomIds(roomIdsByUserId);

        List<Alarm> sortedAlarmList = roomIdsByUserId.stream()
                .flatMap(roomId -> alarmList.stream()
                        .filter(alarm -> alarm.getRoom().getId().equals(roomId)))
                .collect(Collectors.toList());

        return EntityDtoMapper.INSTANCE.toRoomResList(sortedAlarmList);
    }


    public RoomUserMissionLogRes getRoomUserLogList(Integer userId, Integer roomId,
                                                    String localDateTimeString) {

        LocalDateTime localDateTime = timeFormatter.stringToLocalDateTime(localDateTimeString);
        List<RoomUser> roomUserList = roomUserRepository.findAllByRoom_IdOrderByPreference(roomId,
                userId);
        if (roomUserList.isEmpty()) {
            throw new BaseException(Status.ROOM_USER_NOT_FOUND);
        }
        RoomUserMissionLogRes roomUserMissionLogRes = new RoomUserMissionLogRes();
        roomUserMissionLogRes.setName(roomUserList.get(0).getRoom().getName());


        UserAvatar userAvatar = userAvatarRepository.findByUser_IdAndIsActiveIsTrue(userId)
                .orElseThrow(() -> new BaseException(Status.FIND_USER_AVATAR_FAIL));

        roomUserMissionLogRes.setTheme(userAvatar.getAvatar().getTheme().getValue());
        roomUserMissionLogRes.setUserLogList(
                EntityDtoMapper.INSTANCE.toUserLogDataList(roomUserList));

        return setUserLogData(roomUserMissionLogRes, userId, roomId, localDateTime);

    }

    public RoomUserMissionLogRes setUserLogData(RoomUserMissionLogRes roomUserMissionLogRes,
                                                Integer userId, Integer roomId, LocalDateTime localDateTime) {

        boolean isAlarmActive = alarmRepository.findFirstByRoom_Id(roomId)
                .getDayOfWeekValue(localDateTime.getDayOfWeek());
        if (!isAlarmActive) {
            for (RoomUserMissionLogRes.UserLogData userLogData : roomUserMissionLogRes.getUserLogList()) {
                userLogData.setUserCompleteType(UserCompleteType.NOT_MISSION);
                userLogData.setMissionPicLink(Constant.ROOM_USER_MISSION_IMG_NOT_MISSION);
                if (userLogData.getUserId() == userId) {
                    userLogData.setIsMyLog(true);
                } else {
                    userLogData.setIsMyLog(false);
                }

            }
            return roomUserMissionLogRes;
        }

        List<MissionLog> missionLogList = missionLogRepository.findAllByRoom_IdAndCreatedAtContaining(roomId, localDateTime.toLocalDate());

        boolean isToday = localDateTime.toLocalDate().isEqual(LocalDate.now());


        for (RoomUserMissionLogRes.UserLogData userLogData : roomUserMissionLogRes.getUserLogList()) {

            if (userLogData.getUserId() == userId) {
                userLogData.setIsMyLog(true);
            } else {
                userLogData.setIsMyLog(false);
            }
            for (MissionLog missionLog : missionLogList) {
                if (userLogData.getUserId() == missionLog.getUser().getId()) {
                    userLogData.setMissionPicLink(missionLog.getMissionPicLink());
                    userLogData.setUserCompleteType(UserCompleteType.SUCCESS);
                }
            }
            if (Objects.isNull(userLogData.getMissionPicLink())) {
                if (isToday) {
                    Alarm alarm = alarmRepository.findFirstByRoom_Id(roomId);
                    LocalTime alarmLocalTime = alarm.getAlarmTime();
                    LocalTime alarmOffTime = alarmLocalTime.withMinute(alarmLocalTime.getMinute()
                            + alarm.getSnoozeCnt() * alarm.getSnoozeInterval());
                    boolean isBeforeAlarmEnd = localDateTime.toLocalTime().isBefore(alarmOffTime);
                    if (isBeforeAlarmEnd) {
                        userLogData.setMissionPicLink(Constant.ROOM_USER_MISSION_IMG_WAITING);
                        userLogData.setUserCompleteType(UserCompleteType.WAITING);
                    } else {
                        userLogData.setMissionPicLink(Constant.ROOM_USER_MISSION_IMG_FAIL);
                        userLogData.setUserCompleteType(UserCompleteType.FAIL);
                    }

                } else {
                    userLogData.setMissionPicLink(Constant.ROOM_USER_MISSION_IMG_FAIL);
                    userLogData.setUserCompleteType(UserCompleteType.FAIL);
                }
            }

        }

        return roomUserMissionLogRes;
    }


    @Transactional
    public void leaveRoom(Integer roomId, Integer userId) {

        RoomUser roomUser = roomUserRepository.findByRoom_IdAndUser_Id(roomId, userId)
                .orElseThrow(() -> new BaseException(Status.ROOM_USER_NOT_FOUND));
        if (Objects.isNull(roomUser)) {
            throw new BaseException(Status.ROOM_USER_NOT_FOUND);
        }

        roomUserRepository.deleteByRoom_IdAndUser_Id(roomId, userId);
        deleteUnnecessaryRoomAndAlarm(roomId, userId);

    }

    public boolean isEmptyRoom(Integer roomHeadCount) {
        return roomHeadCount == 0;
    }


    @Transactional
    public void deleteUnnecessaryRoomAndAlarm(Integer roomId, Integer userId) {
        Integer roomHeadCount = roomUserRepository.countByRoomId(roomId);
        Alarm alarm = alarmRepository.findByUser_IdAndRoom_Id(userId, roomId)
                .orElseThrow(() -> new BaseException(Status.ALARM_NOT_FOUND));
        alarmRepository.delete(alarm);
            if(isEmptyRoom(roomHeadCount))
                roomRepository.deleteById(roomId);

    }


    public RoomDetailRes getRoomDetail(Integer roomId, Integer userId) {

        Alarm alarm = alarmRepository.findByUser_IdAndRoom_Id(userId, roomId)
                .orElseThrow(() -> new BaseException(Status.ALARM_NOT_FOUND));

        List<RoomUser> roomUsers = roomUserRepository.findAllByRoom_IdOrderByPreference(roomId, userId);

        RoomDetailRes roomDetailRes = new RoomDetailRes();
        roomDetailRes.setRoomData(EntityDtoMapper.INSTANCE.toRoomDetailResRoomData(alarm));
        roomDetailRes.setMissionData(EntityDtoMapper.INSTANCE.toRoomDetailResMissionData(alarm));
        roomDetailRes.setUserList(EntityDtoMapper.INSTANCE.toUserDataList(roomUsers));

        setUserTheme(roomDetailRes);
        roomDetailRes.getRoomData().setCreatedAt(
                timeFormatter.timestampToDotDateFormat(alarm.getRoom().getCreatedAt()));

        roomDetailRes.getRoomData().setHeadCount(roomUsers.size());

        return roomDetailRes;
    }

    public void setUserTheme(RoomDetailRes roomDetailRes) {

        roomDetailRes.getUserList().forEach(userData -> userData.setTheme(
                userAvatarRepository.findByUser_IdAndIsActiveIsTrue(userData.getUserId())
                        .orElseThrow(() -> new BaseException(Status.FIND_USER_AVATAR_FAIL))
                        .getAvatar().getTheme().getValue()));

    }


    @Transactional
    public void updateAgreePush(Integer roomId, Integer userId, boolean agreePush) {

        RoomUser roomUser = roomUserRepository.findByRoom_IdAndUser_Id(roomId, userId)
                .orElseThrow(() -> new BaseException(Status.ROOM_USER_NOT_FOUND));

        roomUser.setAgreePush(agreePush);

    }

    @Transactional
    public void createAlarmAndJoinRoom(Integer roomId, Integer invitedUserId, AlarmCreateReq alarmCreateReq) {
        PostAlarmReq postAlarmReq = alarmMigrationMapper.convertAlarmCreateReqToPostAlarmReq(alarmCreateReq);
        Alarm alarm = alarmService.createAlarmDeprecated(invitedUserId, postAlarmReq);
        alarmRepository.updateRoomIdByAlarmId(alarm.getId(), roomId);
        joinRoom(roomId, invitedUserId);
    }

    @Transactional
    public void joinRoom(Integer roomId, Integer invitedUserId) {


        User user = findExistingUser(userRepository, invitedUserId);


        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new BaseException(Status.ROOM_NOT_FOUND));

        boolean isAlreadyJoin = roomUserRepository.existsRoomUserByRoom_IdAndUser_Id(roomId, invitedUserId);
        if (isAlreadyJoin)
            throw new BaseException(Status.ROOM_USER_ALREADY_EXIST);

        RoomUser roomUser = RoomUser.builder()
                .user(user)
                .room(room)
                .agreePush(true)
                .build();

        roomUserRepository.save(roomUser);
    }

    public RoomInfoRes getRoomInformation(String invitationCode) {

        Room room = roomRepository.findByInvitationCode(invitationCode)
                .orElseThrow(() -> new BaseException(Status.ROOM_NOT_FOUND));

        Integer roomHeadCount = roomUserRepository.countByRoomId(room.getId());
        if (isRoomEmpty(roomHeadCount))
            throw new BaseException(Status.ROOM_NOT_FOUND);

        MissionObject roomMissionObject = alarmRepository.findMissionObjectByRoomId(room.getId());


        return RoomInfoRes.builder()
                .id(room.getId())
                .icon(roomMissionObject.getIcon())
                .name(room.getName())
                .intro(room.getIntro())
                .createdAt(timeFormatter.timestampToDotDateFormat(room.getCreatedAt()))
                .headCount(roomHeadCount)
                .missionObjectId(roomMissionObject.getId())
                .missionKr(roomMissionObject.getKr())
                .build();
    }

    public boolean isRoomEmpty(Integer roomPersonnel) {
        return roomPersonnel <= 0;
    }

}
