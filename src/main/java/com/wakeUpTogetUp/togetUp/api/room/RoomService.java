package com.wakeUpTogetUp.togetUp.api.room;

import com.wakeUpTogetUp.togetUp.api.alarm.AlarmRepository;
import com.wakeUpTogetUp.togetUp.api.alarm.AlarmService;
import com.wakeUpTogetUp.togetUp.api.alarm.model.Alarm;
import com.wakeUpTogetUp.togetUp.api.mission.MissionLogRepository;
import com.wakeUpTogetUp.togetUp.api.mission.MissionRepository;
import com.wakeUpTogetUp.togetUp.api.mission.model.Mission;
import com.wakeUpTogetUp.togetUp.api.mission.model.MissionLog;
import com.wakeUpTogetUp.togetUp.api.room.dto.response.RoomRes;
import com.wakeUpTogetUp.togetUp.api.room.model.Room;
import com.wakeUpTogetUp.togetUp.api.room.model.RoomUser;
import com.wakeUpTogetUp.togetUp.api.users.UserRepository;
import com.wakeUpTogetUp.togetUp.api.users.model.User;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.api.room.dto.request.RoomReq;
import com.wakeUpTogetUp.togetUp.utils.mapper.GroupMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.wakeUpTogetUp.togetUp.utils.mapper.EntityDtoMapper;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final AlarmService alarmService;
    private final UserRepository userRepository;
    private final RoomUserRepository roomUserRepository;
    private final AlarmRepository alarmRepository;
    private final MissionRepository missionRepository;
    private final MissionLogRepository missionLogRepository;
    @Transactional
    public  void createRoom(Integer userId, RoomReq roomReq){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(Status.USER_NOT_FOUND));

        //그룹 만들기
        Room room = Room.builder()
                .name(roomReq.getName())
                .intro(roomReq.getIntro())
                .build();
        //알람 만들기

        Integer alarmId = alarmService.createAlarm(userId,roomReq.getPostAlarmReq()).getId();

        //그룹 만들기
        roomRepository.save(room);

        //유저 그룹에 넣기
        RoomUser roomUser =RoomUser.builder()
                .user(user)
                .room(room)
                .build();
        roomUserRepository.save(roomUser);

        //알람에 room넣기

        alarmRepository.updateRoomIdByAlarmId(alarmId,room.getId());

    }


    public List<RoomRes> getRoomList(Integer userId) {
        List<Alarm> alarmList =  alarmRepository.findAllByUser_IdAndRoom_IdIsNotNull(userId);

        return EntityDtoMapper.INSTANCE.toRoomResList(alarmList);
    }

    @Transactional
    public RoomRes editGroup(Integer groupId , RoomReq roomReq){
    // 그룹수정 이름, 인트로
        Optional<Room> group = Optional.ofNullable(roomRepository.findById(groupId)
                .orElseThrow(
                        () -> new BaseException(Status.INVALID_GROUP_ID)
                ));
        group.get().setName(roomReq.getName());
        group.get().setIntro(roomReq.getIntro());

    //저장
        Room modifiedRoom = roomRepository.save(group.get());


    //반환
        RoomRes roomRes = GroupMapper.INSTANCE.toGroupRes(modifiedRoom);

        return roomRes;
    }






}
