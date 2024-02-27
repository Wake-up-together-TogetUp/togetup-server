package com.wakeUpTogetUp.togetUp.api.alarm;

import com.wakeUpTogetUp.togetUp.api.alarm.dto.request.PatchAlarmReq;
import com.wakeUpTogetUp.togetUp.api.alarm.dto.request.PostAlarmReq;
import com.wakeUpTogetUp.togetUp.api.alarm.dto.response.AlarmSimpleRes;
import com.wakeUpTogetUp.togetUp.api.alarm.dto.response.AlarmTimeLineRes;
import com.wakeUpTogetUp.togetUp.api.alarm.model.Alarm;
import com.wakeUpTogetUp.togetUp.api.mission.MissionObjectRepository;
import com.wakeUpTogetUp.togetUp.api.mission.MissionRepository;
import com.wakeUpTogetUp.togetUp.api.mission.model.Mission;
import com.wakeUpTogetUp.togetUp.api.mission.model.MissionObject;
import com.wakeUpTogetUp.togetUp.api.users.UserRepository;
import com.wakeUpTogetUp.togetUp.api.users.model.User;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.utils.DateTimeProvider;
import com.wakeUpTogetUp.togetUp.utils.mapper.EntityDtoMapper;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.wakeUpTogetUp.togetUp.api.users.UserServiceHelper.*;

@Service
@RequiredArgsConstructor
public class AlarmService {
    private final AlarmRepository alarmRepository;
    private final UserRepository userRepository;
    private final MissionRepository missionRepository;
    private final MissionObjectRepository missionObjectRepository;

    @Transactional
    public Alarm createAlarm(Integer userId, PostAlarmReq postAlarmReq) {
        User user = findExistingUser(userRepository, userId);

        Mission mission = null;
        MissionObject missionObject = null;

        if (postAlarmReq.getMissionId() != null) {
            mission = missionRepository.findById(postAlarmReq.getMissionId())
                    .orElseThrow(() -> new BaseException(Status.MISSION_NOT_FOUND));

            if (postAlarmReq.getMissionObjectId() != null) {
                missionObject = missionObjectRepository.findById(postAlarmReq.getMissionObjectId())
                        .orElseThrow(() -> new BaseException(Status.OBJECT_NOT_FOUND));

                if (missionObject.getMission().getId() != mission.getId()) {
                    throw new BaseException(Status.MISSION_ID_NOT_MATCH);
                }
            }
        }

        Alarm alarm = Alarm.builder()
                .name(postAlarmReq.getName())
                .icon(postAlarmReq.getIcon())
                .snoozeInterval(postAlarmReq.getSnoozeInterval())
                .snoozeCnt(postAlarmReq.getSnoozeCnt())
                .alarmTime(LocalTime.parse(postAlarmReq.getAlarmTime()))
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
                .build();

        return alarmRepository.save(alarm);
    }

    @Transactional
    public Alarm updateAlarm(Integer userId, Integer alarmId, PatchAlarmReq patchAlarmReq) {
        // 알람 수정
        Alarm alarm = alarmRepository.findById(alarmId, userId)
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
                patchAlarmReq.getIcon(),
                patchAlarmReq.getSnoozeInterval(),
                patchAlarmReq.getSnoozeCnt(),
                LocalTime.parse(patchAlarmReq.getAlarmTime()),
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

    @Transactional
    public int deleteAlarm(Integer alarmId) {
        Alarm alarm = alarmRepository.findById(alarmId)
                .orElseThrow(() -> new BaseException(Status.ALARM_NOT_FOUND));
        alarmRepository.delete(alarm);

        return alarm.getId();
    }

    public AlarmTimeLineRes getAlarmTimeLineByUserId(Integer userId) {
        LocalDate today = DateTimeProvider.getCurrentDateInSeoul();
        String dayOfWeek = today.getDayOfWeek().name();

        List<Alarm> todayAlarms = alarmRepository.findTodayAlarmsByUserId(userId, dayOfWeek);
        AlarmSimpleRes nextAlarm = EntityDtoMapper.INSTANCE.toAlarmSimpleRes(getNextAlarm(todayAlarms));
        List<AlarmSimpleRes> alarmSimpleResList = EntityDtoMapper.INSTANCE.toAlarmSimpleResList(todayAlarms);

        return AlarmTimeLineRes.builder()
                .today(today)
                .dayOfWeek(dayOfWeek)
                .nextAlarm(nextAlarm)
                .todayAlarmList(alarmSimpleResList)
                .build();
    }

    private Alarm getNextAlarm(List<Alarm> alarms) {
        LocalTime now = LocalTime.now();

        return alarms.stream()
                .filter(alarm -> alarm.getAlarmTime().isAfter(now))
                .findFirst()
                .orElse(null);
    }
}
