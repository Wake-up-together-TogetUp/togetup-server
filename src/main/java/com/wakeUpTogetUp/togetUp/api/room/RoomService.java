package com.wakeUpTogetUp.togetUp.api.room;

import com.wakeUpTogetUp.togetUp.api.alarm.AlarmRepository;
import com.wakeUpTogetUp.togetUp.api.alarm.AlarmService;
import com.wakeUpTogetUp.togetUp.api.alarm.model.Alarm;
import com.wakeUpTogetUp.togetUp.api.mission.MissionLogRepository;
import com.wakeUpTogetUp.togetUp.api.mission.model.MissionLog;
import com.wakeUpTogetUp.togetUp.api.room.dto.request.RoomUserLogMissionReq;
import com.wakeUpTogetUp.togetUp.api.room.dto.response.RoomUserMissionLogRes;
import com.wakeUpTogetUp.togetUp.api.room.dto.response.RoomRes;
import com.wakeUpTogetUp.togetUp.api.room.model.Room;
import com.wakeUpTogetUp.togetUp.api.room.model.RoomUser;
import com.wakeUpTogetUp.togetUp.api.users.UserRepository;
import com.wakeUpTogetUp.togetUp.api.users.model.User;
import com.wakeUpTogetUp.togetUp.common.Constant;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.api.room.dto.request.RoomReq;
import com.wakeUpTogetUp.togetUp.utils.TimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

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
    private final MissionLogRepository missionLogRepository;
    private final TimeFormatter timeFormatter;
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
        List<Alarm> alarmList =  alarmRepository.findAllByUser_IdAndRoom_IdIsNotNullOrderByAlarmTime(userId);

        return EntityDtoMapper.INSTANCE.toRoomResList(alarmList);
    }


    public RoomUserMissionLogRes getRoomUserLogList(Integer userId, RoomUserLogMissionReq roomUserLogMissionReq){
        Integer roomId = roomUserLogMissionReq.getRoomId();
        LocalDateTime localDateTime = roomUserLogMissionReq.getLocalDateTime();
        boolean isAlarmActive = roomUserLogMissionReq.getIsAlarmActive();

        List<RoomUser> roomUserList = roomUserRepository.findAllByRoom_Id(roomId);
        //크기가 0이면 예외처리
        if(roomUserList.isEmpty())
            throw new BaseException(Status.ROOM_USER_NOT_FOUND);
        //룸이름, userId,userName 매핑 (항상 반환하는 정보)
        RoomUserMissionLogRes roomUserMissionLogRes =new RoomUserMissionLogRes();
        roomUserMissionLogRes.setName(roomUserList.get(0).getRoom().getName());
        roomUserMissionLogRes.setUserLogList(EntityDtoMapper.INSTANCE.toUserLogDataList(roomUserList));

        //isMyLog, missionPicLink, userCompleteType 는 각케이스에 맞게 설정
        return setUserLogData(roomUserMissionLogRes, userId, roomId, isAlarmActive, localDateTime);

    }

    public RoomUserMissionLogRes setUserLogData(RoomUserMissionLogRes roomUserMissionLogRes, Integer userId, Integer roomId, boolean isAlarmActive, LocalDateTime localDateTime){
        if(!isAlarmActive)
        {
            for (RoomUserMissionLogRes.UserLogData userLogData : roomUserMissionLogRes.getUserLogList()) {
                userLogData.setUserCompleteType(UserCompleteType.NOT_MISSION);
                userLogData.setMissionPicLink(Constant.ROOM_USER_MISSION_IMG_NOT_MISSION);
                if(userLogData.getUserId()==userId)
                {
                    userLogData.setIsMyLog(true);
                }

            }
            return roomUserMissionLogRes;
        }
        //missionLog에서 날짜로 가져옴 .
        List<MissionLog> missionLogList = missionLogRepository.findAllByRoom_IdAndCreatedAtContaining(roomId,localDateTime.toLocalDate());
        //오늘인지 아닌지
        boolean isToday = localDateTime.toLocalDate().isEqual(LocalDate.now());

        // for 문돌면서 : 유저 Id로 비교 :
        for (RoomUserMissionLogRes.UserLogData userLogData : roomUserMissionLogRes.getUserLogList()) {
            // 유저 Id가 있으면 (미션 수행한 기록이 있으면) -> 미션 수행사진 매핑
            if(userLogData.getUserId()==userId)
            {
                userLogData.setIsMyLog(true);
            }
            for(MissionLog missionLog:missionLogList){
                if(userLogData.getUserId()==missionLog.getUser().getId()) {
                    userLogData.setMissionPicLink(missionLog.getMissionPicLink());
                    userLogData.setUserCompleteType(UserCompleteType.SUCCESS);
                }
            }
            //유저 Id가 없으면 (미션 수행한 기록이 없으면)
            // -> 오늘날짜이고 알람끝나기 전이면 waiting. 오늘날짜이고 알람이 끝나면 fail (다시울림 포함해서 알람이 끝나면)
            //과거 : fail
            if(Objects.isNull(userLogData.getMissionPicLink()))
            {
                if(isToday)
                {
                    //알람의 다시 울림 시간을 계산함.
                    Alarm alarm = alarmRepository.findFirstByRoom_Id(roomId);
                    LocalTime alarmLocalTime = timeFormatter.stringToLocalTime(alarm.getAlarmTime());
                    LocalTime alarmOffTime = alarmLocalTime.withMinute(alarmLocalTime.getMinute() + alarm.getSnoozeCnt()*alarm.getSnoozeInterval());
                    //알람이 끝나기 전인지 여부
                    boolean isBeforeAlarmEnd = localDateTime.toLocalTime().isBefore(alarmOffTime);

                    //알람이 끝나기 전이라면 waiting 상태로 설정
                    if(isBeforeAlarmEnd)
                    {
                        userLogData.setMissionPicLink(Constant.ROOM_USER_MISSION_IMG_WAITING);
                        userLogData.setUserCompleteType(UserCompleteType.WAITING);
                    }else{ //알람이 끝났다면 실패상태로 설정
                        userLogData.setMissionPicLink(Constant.ROOM_USER_MISSION_IMG_FAIL);
                        userLogData.setUserCompleteType(UserCompleteType.FAIL);
                    }

                }else {//오늘이 아니라 과거라면 실패
                    userLogData.setMissionPicLink(Constant.ROOM_USER_MISSION_IMG_FAIL);
                    userLogData.setUserCompleteType(UserCompleteType.FAIL);
                }
            }

        }



        return roomUserMissionLogRes;
    }



}
