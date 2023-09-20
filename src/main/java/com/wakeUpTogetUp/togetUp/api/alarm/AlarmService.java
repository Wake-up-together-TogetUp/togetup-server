package com.wakeUpTogetUp.togetUp.api.alarm;

import com.wakeUpTogetUp.togetUp.api.alarm.dto.request.PatchAlarmReq;
import com.wakeUpTogetUp.togetUp.api.alarm.model.Alarm;
import com.wakeUpTogetUp.togetUp.api.alarm.dto.request.PostAlarmReq;
import com.wakeUpTogetUp.togetUp.api.mission.MissionObjectRepository;
import com.wakeUpTogetUp.togetUp.api.mission.model.MissionObject;
import com.wakeUpTogetUp.togetUp.api.room.RoomRepository;
import com.wakeUpTogetUp.togetUp.api.room.model.Room;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.api.mission.MissionRepository;
import com.wakeUpTogetUp.togetUp.api.mission.model.Mission;
import com.wakeUpTogetUp.togetUp.api.users.UserRepository;
import com.wakeUpTogetUp.togetUp.api.users.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final UserRepository userRepository;
    private final MissionRepository missionRepository;
    private final MissionObjectRepository missionObjectRepository;
    private final RoomRepository roomRepository;

    // 알람 생성
    @Transactional
    public Alarm createAlarm(Integer userId, PostAlarmReq postAlarmReq) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(Status.USER_NOT_FOUND));
        Room room = postAlarmReq.getRoomId() != null
                ? roomRepository.findById(postAlarmReq.getRoomId()).orElseThrow(() -> new BaseException(Status.INVALID_GROUP_ID))
                : null;
        Mission mission = null;
        MissionObject missionObject = null;

        if(postAlarmReq.getMissionId() != null) {
            mission= missionRepository.findById(postAlarmReq.getMissionId())
                    .orElseThrow(() -> new BaseException(Status.INVALID_MISSION_ID));

            if(postAlarmReq.getMissionObjectId() != null) {
                missionObject = missionObjectRepository.findById(postAlarmReq.getMissionObjectId())
                        .orElseThrow(() -> new BaseException(Status.INVALID_MISSION_OBJECT_ID));

                if(missionObject.getMission().getId() != mission.getId())
                    throw new BaseException(Status.MISSION_ID_NOT_MATCH);
            }
        }

        Alarm alarm = Alarm.builder()
                .name(postAlarmReq.getName())
                .icon(postAlarmReq.getIcon())
                .snoozeInterval(postAlarmReq.getSnoozeInterval())
                .snoozeCnt(postAlarmReq.getSnoozeCnt())
                .alarmTime(postAlarmReq.getAlarmTime())
                .monday(postAlarmReq.getMonday())
                .tuesday(postAlarmReq.getTuesday())
                .wednesday(postAlarmReq.getWednesday())
                .thursday(postAlarmReq.getThursday())
                .friday(postAlarmReq.getFriday())
                .saturday(postAlarmReq.getSaturday())
                .sunday(postAlarmReq.getSunday())
                .isSnoozeActivated(postAlarmReq.getIsSnoozeActivated())
                .isVibrate(postAlarmReq.getIsVibrate())
                .user(user)
                .mission(mission)
                .missionObject(missionObject)
                .room(room)
                .build();

        return alarmRepository.save(alarm);
    }

    // 알람 수정
    @Transactional
    public Alarm updateAlarm(Integer userId, Integer alarmId, PatchAlarmReq patchAlarmReq) {
        // 알람 수정
        Alarm alarm = alarmRepository.findById(alarmId, userId)
                .orElseThrow(() -> new BaseException(Status.ALARM_NOT_FOUND));
        Mission mission = null;
        MissionObject missionObject = null;

        if(patchAlarmReq.getMissionId() != null) {
            mission = missionRepository.findById(patchAlarmReq.getMissionId())
                    .orElseThrow(() -> new BaseException(Status.INVALID_MISSION_ID));

            if (patchAlarmReq.getMissionObjectId() != null) {
                missionObject = missionObjectRepository.findById(patchAlarmReq.getMissionObjectId())
                        .orElseThrow(() -> new BaseException(Status.INVALID_MISSION_OBJECT_ID));

                if (missionObject.getMission().getId() != mission.getId())
                    throw new BaseException(Status.MISSION_ID_NOT_MATCH);
            }
        }

        alarm.modifyProperties(
                patchAlarmReq.getName(),
                patchAlarmReq.getIcon(),
                patchAlarmReq.getSnoozeInterval(),
                patchAlarmReq.getSnoozeCnt(),
                patchAlarmReq.getAlarmTime(),
                patchAlarmReq.getMonday(),
                patchAlarmReq.getTuesday(),
                patchAlarmReq.getWednesday(),
                patchAlarmReq.getThursday(),
                patchAlarmReq.getFriday(),
                patchAlarmReq.getSaturday(),
                patchAlarmReq.getSunday(),
                patchAlarmReq.getIsVibrate(),
                patchAlarmReq.getIsSnoozeActivated(),
                patchAlarmReq.getIsActivated(),
                mission,
                missionObject
        );

        return alarmRepository.save(alarm);
    }

    // 알람 삭제
    @Transactional
    public int deleteAlarm(Integer alarmId) {
        Alarm alarm = alarmRepository.findById(alarmId)
                .orElseThrow(() -> new BaseException(Status.ALARM_NOT_FOUND));
        alarmRepository.delete(alarm);

        return alarm.getId();
    }
    //String 인 alarmTime을 LocalTime으로 변경
    public LocalTime alarmTimeStringToLocalTime(String alarmTime)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime localTime = LocalTime.parse(alarmTime, formatter);
        return localTime;

    }
}
