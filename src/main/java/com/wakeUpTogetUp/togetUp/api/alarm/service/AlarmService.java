package com.wakeUpTogetUp.togetUp.api.alarm.service;

import static com.wakeUpTogetUp.togetUp.api.users.UserServiceHelper.findExistingUser;

import com.wakeUpTogetUp.togetUp.api.alarm.controller.dto.request.PatchAlarmReq;
import com.wakeUpTogetUp.togetUp.api.alarm.controller.dto.request.PostAlarmReq;
import com.wakeUpTogetUp.togetUp.api.alarm.model.Alarm;
import com.wakeUpTogetUp.togetUp.api.alarm.repository.AlarmRepository;
import com.wakeUpTogetUp.togetUp.api.mission.model.Mission;
import com.wakeUpTogetUp.togetUp.api.mission.model.MissionObject;
import com.wakeUpTogetUp.togetUp.api.mission.repository.MissionObjectRepository;
import com.wakeUpTogetUp.togetUp.api.mission.repository.MissionRepository;
import com.wakeUpTogetUp.togetUp.api.users.UserRepository;
import com.wakeUpTogetUp.togetUp.api.users.domain.User;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmValidationService alarmValidationService;

    private final AlarmRepository alarmRepository;
    private final UserRepository userRepository;
    private final MissionRepository missionRepository;
    private final MissionObjectRepository missionObjectRepository;

    @Transactional
    public Alarm create(Integer userId, PostAlarmReq postAlarmReq) {
        User user = findExistingUser(userRepository, userId);

        Mission mission = null;
        MissionObject missionObject = null;

        if (postAlarmReq.getMissionId() != null) {
            mission = missionRepository.findById(postAlarmReq.getMissionId())
                    .orElseThrow(() -> new BaseException(Status.MISSION_NOT_FOUND));

            if (postAlarmReq.getMissionObjectId() != null) {
                missionObject = missionObjectRepository.findById(postAlarmReq.getMissionObjectId())
                        .orElseThrow(() -> new BaseException(Status.MISSION_OBJECT_NOT_FOUND));

                if (missionObject.getMission().getId() != mission.getId()) {
                    throw new BaseException(Status.MISSION_ID_NOT_MATCH);
                }
            }
        }

        Alarm alarm = Alarm.create(
                postAlarmReq.getName(),
                LocalTime.parse(postAlarmReq.getAlarmTime()),
                postAlarmReq.getMonday(),
                postAlarmReq.getTuesday(),
                postAlarmReq.getWednesday(),
                postAlarmReq.getThursday(),
                postAlarmReq.getFriday(),
                postAlarmReq.getSaturday(),
                postAlarmReq.getSunday(),
                postAlarmReq.getIsVibrate(),
                user, mission, missionObject);

        return alarmRepository.save(alarm);
    }

    @Transactional
    public Alarm updateAlarm(Integer userId, Integer alarmId, PatchAlarmReq patchAlarmReq) {
        Alarm alarm = alarmRepository.findByIdAndUser_Id(alarmId, userId)
                .orElseThrow(() -> new BaseException(Status.ALARM_NOT_FOUND));
        Mission mission = null;
        MissionObject missionObject = null;

        if (patchAlarmReq.getMissionId() != null) {
            mission = missionRepository.findById(patchAlarmReq.getMissionId())
                    .orElseThrow(() -> new BaseException(Status.MISSION_NOT_FOUND));

            if (patchAlarmReq.getMissionObjectId() != null) {
                missionObject = missionObjectRepository.findById(patchAlarmReq.getMissionObjectId())
                        .orElseThrow(() -> new BaseException(Status.MISSION_NOT_FOUND));

                if (!Objects.equals(missionObject.getMission().getId(), mission.getId())) {
                    throw new BaseException(Status.MISSION_ID_NOT_MATCH);
                }
            }
        }

        alarm.modifyProperties(
                patchAlarmReq.getName(),
                LocalTime.parse(patchAlarmReq.getAlarmTime()),
                patchAlarmReq.getMonday(),
                patchAlarmReq.getTuesday(),
                patchAlarmReq.getWednesday(),
                patchAlarmReq.getThursday(),
                patchAlarmReq.getFriday(),
                patchAlarmReq.getSaturday(),
                patchAlarmReq.getSunday(),
                patchAlarmReq.getIsVibrate(),
                patchAlarmReq.getIsActivated(),
                mission,
                missionObject
        );

        return alarmRepository.save(alarm);
    }

    @Transactional
    public int deleteAlarm(Integer alarmId) {
        Alarm alarm = alarmRepository.findById(alarmId)
                .orElseThrow(() -> new BaseException(Status.ALARM_NOT_FOUND));
        alarmRepository.delete(alarm);

        return alarm.getId();
    }

    @Transactional
    public void deactivateAlarms(List<Integer> alarmIds, Integer userId) {
        alarmValidationService.validateUserAlarms(userId, alarmIds);
        alarmRepository.deactivateAlarms(alarmIds);
    }
}
