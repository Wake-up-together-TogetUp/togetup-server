package com.wakeUpTogetUp.togetUp.api.room;

import com.wakeUpTogetUp.togetUp.api.alarm.AlarmRepository;
import com.wakeUpTogetUp.togetUp.api.alarm.AlarmService;
import com.wakeUpTogetUp.togetUp.api.alarm.model.Alarm;
import com.wakeUpTogetUp.togetUp.api.mission.MissionLogRepository;
import com.wakeUpTogetUp.togetUp.api.mission.model.MissionLog;
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
import com.wakeUpTogetUp.togetUp.utils.mapper.EntityDtoMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void createRoom(Integer userId, RoomReq roomReq) {

        //그룹 만들기
        Room room = Room.builder()
                .name(roomReq.getName())
                .intro(roomReq.getIntro())
                .build();
        //알람 만들기
        Integer alarmId = alarmService.createAlarm(userId, roomReq.getPostAlarmReq()).getId();

        //그룹 만들기
        Room savedRoom = roomRepository.save(room);

        //그룹에 유저 넣기
        this.joinRoom(savedRoom.getId(), userId, true);

        //알람에 room넣기
        alarmRepository.updateRoomIdByAlarmId(alarmId, savedRoom.getId());

    }


    public List<RoomRes> getRoomList(Integer userId) {

        //userId로 roomId list 가져오기- createdAt 내림차순으로 가져옴
        List<Integer> roomIdsByUserId = roomUserRepository.findAllRoomIdsByUserId(userId);

        //roomId를 포함하는 알람리스트 가져오기
        List<Alarm> alarmList = alarmRepository.findAllByRoomIds(roomIdsByUserId);

        //room에 들어온 순서대로 정렬
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
        //크기가 0이면 예외처리
        if (roomUserList.isEmpty()) {
            throw new BaseException(Status.ROOM_USER_NOT_FOUND);
        }
        //룸이름, userId,userName 매핑 (항상 반환하는 정보)
        RoomUserMissionLogRes roomUserMissionLogRes = new RoomUserMissionLogRes();
        roomUserMissionLogRes.setName(roomUserList.get(0).getRoom().getName());

        //테마 찾기
//        UserAvatar userAvatar = userAvatarRepository.findByUser_Id(userId);
//        roomUserMissionLogRes.setTheme(AvatarTheme.valueOf(userAvatar.getAvatar().getTheme()).getValue());
        UserAvatar userAvatar = userAvatarRepository.findByUser_IdAndIsActiveIsTrue(userId)
                .orElseThrow(() -> new BaseException(Status.FIND_USER_AVATAR_FAIL));

        roomUserMissionLogRes.setTheme(userAvatar.getAvatar().getTheme().getValue());

        roomUserMissionLogRes.setUserLogList(
                EntityDtoMapper.INSTANCE.toUserLogDataList(roomUserList));

        //isMyLog, missionPicLink, userCompleteType 는 각케이스에 맞게 설정
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
        //missionLog에서 날짜로 가져옴 .
        List<MissionLog> missionLogList = missionLogRepository.findAllByRoom_IdAndCreatedAtContaining(
                roomId, localDateTime.toLocalDate());
        //오늘인지 아닌지
        boolean isToday = localDateTime.toLocalDate().isEqual(LocalDate.now());

        // for 문돌면서 : 유저 Id로 비교 :
        for (RoomUserMissionLogRes.UserLogData userLogData : roomUserMissionLogRes.getUserLogList()) {
            // 유저 Id가 있으면 (미션 수행한 기록이 있으면) -> 미션 수행사진 매핑
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
            //유저 Id가 없으면 (미션 수행한 기록이 없으면)
            // -> 오늘날짜이고 알람끝나기 전이면 waiting. 오늘날짜이고 알람이 끝나면 fail (다시울림 포함해서 알람이 끝나면)
            //과거 : fail
            if (Objects.isNull(userLogData.getMissionPicLink())) {
                if (isToday) {
                    //알람의 다시 울림 시간을 계산함.
                    Alarm alarm = alarmRepository.findFirstByRoom_Id(roomId);
                    LocalTime alarmLocalTime = timeFormatter.stringToLocalTime(
                            alarm.getAlarmTime());
                    LocalTime alarmOffTime = alarmLocalTime.withMinute(alarmLocalTime.getMinute()
                            + alarm.getSnoozeCnt() * alarm.getSnoozeInterval());
                    //알람이 끝나기 전인지 여부
                    boolean isBeforeAlarmEnd = localDateTime.toLocalTime().isBefore(alarmOffTime);

                    //알람이 끝나기 전이라면 waiting 상태로 설정
                    if (isBeforeAlarmEnd) {
                        userLogData.setMissionPicLink(Constant.ROOM_USER_MISSION_IMG_WAITING);
                        userLogData.setUserCompleteType(UserCompleteType.WAITING);
                    } else { //알람이 끝났다면 실패상태로 설정
                        userLogData.setMissionPicLink(Constant.ROOM_USER_MISSION_IMG_FAIL);
                        userLogData.setUserCompleteType(UserCompleteType.FAIL);
                    }

                } else {//오늘이 아니라 과거라면 실패
                    userLogData.setMissionPicLink(Constant.ROOM_USER_MISSION_IMG_FAIL);
                    userLogData.setUserCompleteType(UserCompleteType.FAIL);
                }
            }

        }

        return roomUserMissionLogRes;
    }

    @Transactional
    public void changeRoomHost(Integer roomId, Integer userId, Integer selectedUserId) {

        //host false로 바꾸기
        RoomUser roomUser = roomUserRepository.findByRoom_IdAndUser_Id(roomId, userId)
                .orElseThrow(() -> new BaseException(Status.ROOM_USER_NOT_FOUND));
        if (!roomUser.getIsHost()) {
            throw new BaseException(Status.INVALID_ROOM_HOST_ID);
        }
        roomUser.setIsHost(false);

        //선택한 user를 host 지정
        RoomUser seletedRoomUser = roomUserRepository.findByRoom_IdAndUser_Id(roomId, selectedUserId)
                .orElseThrow(() -> new BaseException(Status.ROOM_USER_NOT_FOUND));
        seletedRoomUser.setIsHost(true);

    }

    @Transactional
    public void leaveRoom(Integer roomId, Integer userId) {

        RoomUser roomUser = roomUserRepository.findByRoom_IdAndUser_Id(roomId, userId)
                .orElseThrow(() -> new BaseException(Status.ROOM_USER_NOT_FOUND));
        if (Objects.isNull(roomUser)) {
            throw new BaseException(Status.ROOM_USER_NOT_FOUND);
        }

        //방장이 방을 나갈때 방장이 양도됨.
        if (roomUser.getIsHost()) {
            this.findNextCreatedUser(roomId, roomUser.getId());
        }

        roomUserRepository.deleteByRoom_IdAndUser_Id(roomId, userId);

    }

    @Transactional
    public void findNextCreatedUser(Integer roomId, Integer userId) {
        List<RoomUser> orderedRoomUser = roomUserRepository.findAllByRoom_IdOrderByCreatedAt(
                roomId);
        //방의 마지막 멤버가 나가면 방,알람 삭제
        if (orderedRoomUser.size() < Constant.MINIMUM_NUMBER_TO_CHANGE_HOST) {
            deleteUnnecessaryRoomAndAlarm(roomId);
            return;
        }

        //방장이 제일 먼저 들어온 사람인 경우 그 다음 사람이 방장이 된다.
        Integer nextHostId = orderedRoomUser.get(0).getId();
        if (nextHostId == userId) {
            nextHostId = orderedRoomUser.get(1).getId();
        }

        this.changeRoomHost(roomId, userId, nextHostId);
    }

    @Transactional
    public void deleteUnnecessaryRoomAndAlarm(Integer roomId) {
        Alarm alarm = alarmRepository.findFirstByRoom_Id(roomId);
        if (Objects.isNull(alarm)) {
            throw new BaseException(Status.ALARM_NOT_FOUND);
        }
        alarmRepository.delete(alarm);
        roomRepository.deleteById(roomId);
    }


    public RoomDetailRes getRoomDetail(Integer roomId, Integer userId) {
        //알람 조회
        Alarm alarm = alarmRepository.findFirstByRoom_Id(roomId);
        if (Objects.isNull(alarm)) {
            throw new BaseException(Status.ALARM_NOT_FOUND);
        }

        //room_user 조회
        List<RoomUser> roomUsers = roomUserRepository.findAllByRoom_IdOrderByPreference(roomId,
                userId);

        //dto 매핑 mapper 사용
        RoomDetailRes roomDetailRes = new RoomDetailRes();
        roomDetailRes.setRoomData(EntityDtoMapper.INSTANCE.toRoomDetailResRoomData(alarm));
        roomDetailRes.setAlarmData(EntityDtoMapper.INSTANCE.toRoomDetailResAlarmData(alarm));
        roomDetailRes.setUserList(EntityDtoMapper.INSTANCE.toUserDataList(roomUsers));

        //dto 매핑 - 커스텀 필드
        this.setUserTheme(roomDetailRes);
        roomDetailRes.getRoomData().setCreatedAt(
                timeFormatter.timestampToDotDateFormat(alarm.getRoom().getCreatedAt()));

        roomDetailRes.getRoomData().setPersonnel(roomUsers.size());

        // ex) 13:00 -> pm 1:00
        roomDetailRes.getAlarmData()
                .setAlarmTime(timeFormatter.timeStringToAMPMFormat(alarm.getAlarmTime()));
        // ex)  평일, 주말, 매일, 월요일, (월, 화, 수), 빈칸
        roomDetailRes.getAlarmData().setAlarmDay(
                timeFormatter.formatDaysOfWeek(alarm.getMonday(), alarm.getTuesday(),
                        alarm.getWednesday(), alarm.getThursday(), alarm.getFriday(),
                        alarm.getSaturday(), alarm.getSunday()));

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
    public void joinRoom(Integer roomId, Integer invitedUserId, boolean isHost) {

        if (!isHost && roomUserRepository.existsRoomUserByRoom_IdAndUser_Id(roomId, invitedUserId)) {
            throw new BaseException(Status.ROOM_USER_ALREADY_EXIST);
        }

        User user = userRepository.findById(invitedUserId)
                .orElseThrow(() -> new BaseException(Status.USER_NOT_FOUND));

        //그룹 만들기
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new BaseException(Status.ROOM_NOT_FOUND));

        //유저 그룹에 넣기
        RoomUser roomUser = RoomUser.builder()
                .user(user)
                .room(room)
                .isHost(isHost)
                .agreePush(true)
                .build();

        roomUserRepository.save(roomUser);
    }

    public RoomInfoRes getRoomInformation(String invitationCode) {

        Alarm alarm = alarmRepository.findByInvitationCode(invitationCode)
                .orElseThrow(() -> new BaseException(Status.ALARM_NOT_FOUND));

        Integer roomPersonnel = roomUserRepository.countByRoomId(alarm.getRoom().getId());
        if (roomPersonnel < 1)
            throw new BaseException(Status.ROOM_NOT_FOUND);

        //dto 매핑 mapper 사용
        RoomInfoRes roomInfoRes = new RoomInfoRes();
        roomInfoRes.setRoomData(EntityDtoMapper.INSTANCE.toRoomInfoResRoomData(alarm));
        roomInfoRes.setAlarmData(EntityDtoMapper.INSTANCE.toRoomInfoResAlarmData(alarm));

        //dto 매핑 - 커스텀 필드
        roomInfoRes.getRoomData().setCreatedAt(
                timeFormatter.timestampToDotDateFormat(alarm.getRoom().getCreatedAt()));
        roomInfoRes.getRoomData().setPersonnel(roomPersonnel);

        // ex) 13:00 -> pm 1:00
        roomInfoRes.getAlarmData()
                .setAlarmTime(timeFormatter.timeStringToAMPMFormat(alarm.getAlarmTime()));

        // ex)  평일, 주말, 매일, 월요일, (월, 화, 수), 빈칸
        roomInfoRes.getAlarmData().setAlarmDay(
                timeFormatter.formatDaysOfWeek(alarm.getMonday(), alarm.getTuesday(),
                        alarm.getWednesday(), alarm.getThursday(), alarm.getFriday(),
                        alarm.getSaturday(), alarm.getSunday()));

        return roomInfoRes;
    }

}
