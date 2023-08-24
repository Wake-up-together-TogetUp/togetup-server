package com.wakeUpTogetUp.togetUp.api.room;

import com.wakeUpTogetUp.togetUp.api.alarm.AlarmRepository;
import com.wakeUpTogetUp.togetUp.api.alarm.AlarmService;
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

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final AlarmService alarmService;
    private final UserRepository userRepository;
    private final RoomUserRepository roomUserRepository;
    private final AlarmRepository alarmRepository;

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
    public List<RoomRes> getGroup() {

        //모든 그룹 가져오기
        List<Room> roomList = roomRepository.findAll();

        // dto 매핑
        ArrayList<RoomRes> roomResList = new ArrayList<>();
        for(Room room : roomList) {
            roomResList.add(GroupMapper.INSTANCE.toGroupRes(room));
        }

        return roomResList;
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
