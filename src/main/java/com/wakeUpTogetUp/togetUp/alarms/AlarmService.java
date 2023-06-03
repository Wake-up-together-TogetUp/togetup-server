package com.wakeUpTogetUp.togetUp.alarms;

import com.wakeUpTogetUp.togetUp.alarms.dto.request.PatchAlarmReq;
import com.wakeUpTogetUp.togetUp.alarms.dto.response.AlarmRes;
import com.wakeUpTogetUp.togetUp.alarms.model.Alarm;
import com.wakeUpTogetUp.togetUp.alarms.dto.request.PostAlarmReq;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.missions.MissionRepository;
import com.wakeUpTogetUp.togetUp.missions.model.Mission;
import com.wakeUpTogetUp.togetUp.routines.RoutineProvider;
import com.wakeUpTogetUp.togetUp.routines.RoutineService;
import com.wakeUpTogetUp.togetUp.routines.dto.request.PostRoutineReq;
import com.wakeUpTogetUp.togetUp.users.UserRepository;
import com.wakeUpTogetUp.togetUp.users.model.User;
import com.wakeUpTogetUp.togetUp.utils.mapper.EntityDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AlarmService {
    private final RoutineProvider routineProvider;
    private final RoutineService routineService;
    private final AlarmRepository alarmRepository;
    private final UserRepository userRepository;
    private final MissionRepository missionRepository;

    // 알람 생성
    @Transactional
    public AlarmRes createAlarm(Integer userId, PostAlarmReq postAlarmReq) {
        // alarm 생성
        User user = userRepository.findById(userId)
                .orElseThrow(
                () -> new BaseException(Status.INVALID_USER_ID)
        );

        Mission mission = missionRepository.findById(postAlarmReq.getMissionId())
                .orElseThrow(
                        () -> new BaseException(Status.INVALID_MISSION_ID)
                );

        Alarm alarm = Alarm.builder()
                .user(user)
                .mission(mission)
                .name(postAlarmReq.getName())
                .icon(postAlarmReq.getIcon())
                .sound(postAlarmReq.getSound())
                .isVibrate(postAlarmReq.getIsVibrate())
                .isRoutineOn(postAlarmReq.getIsRoutineOn())
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
                .isActivated(postAlarmReq.getIsActivated())
                .build();

        // 영속성 컨텍스트의 특성으로 default 값으로 넣은 속성은 값이 null 로 나오는 것 같다.
        Alarm alarmCreated = alarmRepository.save(alarm);
        AlarmRes alarmRes = EntityDtoMapper.INSTANCE.toAlarmRes(alarmCreated);

        // 루틴 리스트가 있으면
        if(!Objects.isNull(postAlarmReq.getRoutineList())) {
            // 루틴 리스트 생성
            for(PostRoutineReq routineReq : postAlarmReq.getRoutineList()) {
                routineService.createRoutine(routineReq, alarmCreated);
            }
            alarmRes.setRoutineResList(routineProvider.getRoutinesByAlarmId(alarmCreated.getId()));
        }
        return alarmRes;
    }

    // 알람 수정
    @Transactional
    public AlarmRes updateAlarm(Integer userId, Integer alarmId, PatchAlarmReq patchAlarmReq) {
        // 알람 수정
        Alarm alarm = alarmRepository.findById(alarmId, userId)
                .orElseThrow(
                () -> new BaseException(Status.INVALID_ALARM_ID)
        );

        Mission mission = missionRepository.findById(patchAlarmReq.getMissionId())
                .orElseThrow(
                        () -> new BaseException(Status.INVALID_MISSION_ID)
                );

        alarm.modifyProperties(
                mission,
                patchAlarmReq.getName(),
                patchAlarmReq.getIcon(),
                patchAlarmReq.getSound(),
                patchAlarmReq.getIsVibrate(),
                patchAlarmReq.getIsRoutineOn(),
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
                patchAlarmReq.getIsActivated()
        );
        Alarm alarmModified = alarmRepository.save(alarm);
        AlarmRes alarmRes = EntityDtoMapper.INSTANCE.toAlarmRes(alarmModified);

        // 연관 루틴 삭제
        routineService.deleteRoutinesByAlarmId(alarmId);

        // 루틴 리스트 재생성
        if(!Objects.isNull(patchAlarmReq.getRoutineList())) {
            // 루틴 리스트 생성
            for(PostRoutineReq routineReq : patchAlarmReq.getRoutineList()) {
                routineService.createRoutine(routineReq, alarmModified);
            }
            alarmRes.setRoutineResList(routineProvider.getRoutinesByAlarmId(alarmModified.getId()));
        }

        return alarmRes;
    }

    // 알람 삭제
    @Transactional
    public void deleteAlarm(Integer alarmId) {
        Alarm alarm = alarmRepository.findById(alarmId).orElseThrow(
                () -> new BaseException(Status.INVALID_ALARM_ID)
        );
        alarmRepository.delete(alarm);
    }
}
