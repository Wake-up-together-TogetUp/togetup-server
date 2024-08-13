package com.wakeUpTogetUp.togetUp.api.room;

import static com.wakeUpTogetUp.togetUp.api.users.UserServiceHelper.findExistingUser;

import com.wakeUpTogetUp.togetUp.api.alarm.dto.request.AlarmCreateReq;
import com.wakeUpTogetUp.togetUp.api.alarm.dto.request.PostAlarmReq;
import com.wakeUpTogetUp.togetUp.api.alarm.model.Alarm;
import com.wakeUpTogetUp.togetUp.api.alarm.repository.AlarmRepository;
import com.wakeUpTogetUp.togetUp.api.alarm.service.AlarmService;
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
    public Integer createRoom(Integer userId, RoomReq roomReq) {

        Room room = Room.builder()
                .name(roomReq.getName())
                .intro(roomReq.getIntro())
                .build();

        PostAlarmReq postAlarmReq = alarmMigrationMapper.convertAlarmCreateReqToPostAlarmReq(roomReq.getAlarmCreateReq());

        Integer alarmId = alarmService.create(userId, postAlarmReq).getId();
        Room savedRoom = roomRepository.save(room);
        this.joinRoom(savedRoom.getId(), userId);

        alarmRepository.updateRoomIdByAlarmId(alarmId, savedRoom.getId());

        return alarmId;
    }

    @Transactional
    public void leaveRoom(Integer roomId, Integer userId) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new BaseException(Status.ROOM_NOT_FOUND));

        room.leaveRoom(userId);

        deleteUnnecessaryRoomAndAlarm(room,userId);

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
    public Integer createAlarmAndJoinRoom(Integer roomId, Integer invitedUserId, AlarmCreateReq alarmCreateReq) {
        PostAlarmReq postAlarmReq = alarmMigrationMapper.convertAlarmCreateReqToPostAlarmReq(alarmCreateReq);
        Alarm alarm = alarmService.create(invitedUserId, postAlarmReq);
        alarmRepository.updateRoomIdByAlarmId(alarm.getId(), roomId);
        joinRoom(roomId, invitedUserId);
        return alarm.getId();
    }

    @Transactional
    public void joinRoom(Integer roomId, Integer invitedUserId) {
        User user = findExistingUser(userRepository, invitedUserId);

        Room room = roomRepository.findByIdUsingPessimisticLock(roomId)
                .orElseThrow(() -> new BaseException(Status.ROOM_NOT_FOUND));

        RoomUser roomUser = RoomUser.builder()
                .user(user)
                .room(room)
                .agreePush(true)
                .build();

        room.join((roomUser));
    }
}
