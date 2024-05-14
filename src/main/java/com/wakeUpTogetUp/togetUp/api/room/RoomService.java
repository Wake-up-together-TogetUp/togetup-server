package com.wakeUpTogetUp.togetUp.api.room;

import static com.wakeUpTogetUp.togetUp.api.users.UserServiceHelper.findExistingUser;

import com.wakeUpTogetUp.togetUp.api.alarm.AlarmRepository;
import com.wakeUpTogetUp.togetUp.api.alarm.AlarmService;
import com.wakeUpTogetUp.togetUp.api.alarm.dto.request.AlarmCreateReq;
import com.wakeUpTogetUp.togetUp.api.alarm.dto.request.PostAlarmReq;
import com.wakeUpTogetUp.togetUp.api.alarm.model.Alarm;
import com.wakeUpTogetUp.togetUp.api.room.dto.request.RoomReq;
import com.wakeUpTogetUp.togetUp.api.room.model.Room;
import com.wakeUpTogetUp.togetUp.api.room.model.RoomUser;
import com.wakeUpTogetUp.togetUp.api.users.UserRepository;
import com.wakeUpTogetUp.togetUp.api.users.domain.User;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.utils.mapper.AlarmMigrationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final AlarmService alarmService;
    private final UserRepository userRepository;
    private final RoomUserRepository roomUserRepository;
    private final AlarmRepository alarmRepository;
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

    @Transactional
    public void leaveRoom(Integer roomId, Integer userId) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new BaseException(Status.ROOM_NOT_FOUND));

        if(!room.isUserInRoom(userId)) {
            throw new BaseException(Status.ROOM_USER_NOT_FOUND);
        }

        roomUserRepository.deleteByRoom_IdAndUser_Id(roomId, userId);
        deleteUnnecessaryRoomAndAlarm(room, userId);

    }

    @Transactional
    public void deleteUnnecessaryRoomAndAlarm(Room room, Integer userId) {
        Alarm alarm = alarmRepository.findByUser_IdAndRoom_Id(userId, room.getId())
                .orElseThrow(() -> new BaseException(Status.ALARM_NOT_FOUND));
        alarmRepository.delete(alarm);
            if(room.isEmptyRoom())
                roomRepository.delete(room);

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
}
