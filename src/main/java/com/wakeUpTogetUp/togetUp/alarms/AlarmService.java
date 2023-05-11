package com.wakeUpTogetUp.togetUp.alarms;

import com.wakeUpTogetUp.togetUp.alarms.dto.request.PatchAlarmReq;
import com.wakeUpTogetUp.togetUp.alarms.dto.response.AlarmRes;
import com.wakeUpTogetUp.togetUp.alarms.model.Alarm;
import com.wakeUpTogetUp.togetUp.mappingAlarmRoutine.model.MappingAlarmRoutine;
import com.wakeUpTogetUp.togetUp.alarms.dto.request.PostAlarmReq;
import com.wakeUpTogetUp.togetUp.common.exception.BaseException;
import com.wakeUpTogetUp.togetUp.common.ResponseStatus;
import com.wakeUpTogetUp.togetUp.mappingAlarmRoutine.MappingAlarmRoutineRepository;
import com.wakeUpTogetUp.togetUp.missions.MissionRepository;
import com.wakeUpTogetUp.togetUp.missions.model.Mission;
import com.wakeUpTogetUp.togetUp.routines.RoutineRepository;
import com.wakeUpTogetUp.togetUp.routines.dto.response.RoutineRes;
import com.wakeUpTogetUp.togetUp.routines.model.Routine;
import com.wakeUpTogetUp.togetUp.users.UserRepository;
import com.wakeUpTogetUp.togetUp.users.model.User;
import com.wakeUpTogetUp.togetUp.utils.mappers.AlarmMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmService {
    private final AlarmProvider alarmProvider;
    private final AlarmRepository alarmRepository;
    private final RoutineRepository routineRepository;
    private final UserRepository userRepository;
    private final MappingAlarmRoutineRepository mappingAlarmRoutineRepository;
    private final MissionRepository missionRepository;

    // 알람 생성
    @Transactional
    public int createAlarm(Integer userId, PostAlarmReq postAlarmReq) {
        User user = userRepository.findById(userId)
                .orElseThrow(
                () -> new BaseException(ResponseStatus.INVALID_USER_ID)
        );

        Mission mission = missionRepository.findById(postAlarmReq.getMissionId())
                .orElseThrow(
                        () -> new BaseException(ResponseStatus.INVALID_MISSION_ID)
                );

        Alarm alarm = Alarm.builder()
                .user(user)
                .mission(mission)
                .name(postAlarmReq.getName())
                .icon(postAlarmReq.getIcon())
                .sound(postAlarmReq.getSound())
                .volume(postAlarmReq.getVolume())
                .isVibrate(postAlarmReq.getIsVibrate())
                .isRoutineOn(postAlarmReq.getIsRoutineOn())
                .snoozeInterval(postAlarmReq.getSnoozeInterval())
                .snoozeCnt(postAlarmReq.getSnoozeCnt())
                .startHour(postAlarmReq.getStartHour())
                .startMinute(postAlarmReq.getStartMinute())
                .monday(postAlarmReq.getMonday())
                .tuesday(postAlarmReq.getTuesday())
                .wednesday(postAlarmReq.getWednesday())
                .thursday(postAlarmReq.getThursday())
                .friday(postAlarmReq.getFriday())
                .saturday(postAlarmReq.getSaturday())
                .sunday(postAlarmReq.getSunday())
                .build();

        alarmRepository.save(alarm);

        // 루틴 리스트가 있으면
        if(postAlarmReq.getRoutineIdList() != null) {
            // 매핑 알람 루틴 생성
            createMappingAlarmRoutineList(postAlarmReq, alarm);
        }

        return alarm.getId();
    }

    // 알람 수정
    @Transactional
    public AlarmRes updateAlarm(Integer userId, Integer alarmId, PatchAlarmReq patchAlarmReq) {
        // 추가 값 설정
        patchAlarmReq.setUserId(userId);
        patchAlarmReq.setId(alarmId);

        // 알람 수정
        Alarm alarm = alarmRepository.findById(alarmId, userId)
                .orElseThrow(
                () -> new BaseException(ResponseStatus.INVALID_ALARM_ID)
        );

        Mission mission = missionRepository.findById(patchAlarmReq.getMissionId())
                .orElseThrow(
                        () -> new BaseException(ResponseStatus.INVALID_MISSION_ID)
                );

        alarm.modifyProperties(
                mission,
                patchAlarmReq.getName(),
                patchAlarmReq.getIcon(),
                patchAlarmReq.getSound(),
                patchAlarmReq.getVolume(),
                patchAlarmReq.getIsVibrate(),
                patchAlarmReq.getIsRoutineOn(),
                patchAlarmReq.getSnoozeInterval(),
                patchAlarmReq.getSnoozeCnt(),
                patchAlarmReq.getStartHour(),
                patchAlarmReq.getStartMinute(),
                patchAlarmReq.getMonday(),
                patchAlarmReq.getTuesday(),
                patchAlarmReq.getWednesday(),
                patchAlarmReq.getThursday(),
                patchAlarmReq.getFriday(),
                patchAlarmReq.getSaturday(),
                patchAlarmReq.getSunday()
        );
        Alarm alarmModified = alarmRepository.save(alarm);

        // 루틴 리스트 수정
        // 연관된 매핑 루틴 리스트 삭제
        mappingAlarmRoutineRepository.deleteByAlarmId(alarmId);

        // 매핑 루틴 리스트 재생성
        createMappingAlarmRoutineList(patchAlarmReq, alarmModified);

        // routine response 리스트 가져오기
        List<RoutineRes> routineResList = alarmProvider.getRoutineResByAlarmId(alarmId);

        AlarmRes alarmRes = AlarmMapper.INSTANCE.toAlarmRes(alarmModified, routineResList);

        // return
        return alarmRes;
    }

    // 매핑 알람 루틴 리스트 생성하기 - 생성
    @Transactional
    protected void createMappingAlarmRoutineList(PostAlarmReq postAlarmReq, Alarm alarm){
        int i=1;

        // 매핑 알람-루틴 생성
        for(Integer routineId : postAlarmReq.getRoutineIdList()) {
            Routine routine = routineRepository.findById(routineId)
                    .orElseThrow(
                            () -> new BaseException(ResponseStatus.INVALID_ROUTINE_ID)
                    );
            MappingAlarmRoutine mappingAlarmRoutine = MappingAlarmRoutine.builder()
                    .user(alarm.getUser())
                    .alarm(alarm)
                    .routine(routine)
                    .routineOrder(i)
                    .build();

            mappingAlarmRoutineRepository.save(mappingAlarmRoutine);
            i++;
        }
    }

    // 매핑 알람 루틴 리스트 생성하기 - 수정
    @Transactional
    protected void createMappingAlarmRoutineList(PatchAlarmReq patchAlarmReq, Alarm alarm){
        int i=1;

        // 매핑 알람-루틴 생성
        for(Integer routineId : patchAlarmReq.getRoutineIdList()) {
            Routine routine = routineRepository.findById(routineId)
                    .orElseThrow(
                            () -> new BaseException(ResponseStatus.INVALID_ROUTINE_ID)
                    );
            MappingAlarmRoutine mappingAlarmRoutine = MappingAlarmRoutine.builder()
                    .user(alarm.getUser())
                    .alarm(alarm)
                    .routine(routine)
                    .routineOrder(i)
                    .build();

            mappingAlarmRoutineRepository.save(mappingAlarmRoutine);
            i++;
        }
    }

    // 알람 삭제
    @Transactional
    public void deleteAlarm(Integer alarmId) {
        // 해당 아이디의 알람이 존재하는지 확인
        Alarm alarm = alarmRepository.findById(alarmId).orElseThrow(
                () -> new BaseException(ResponseStatus.INVALID_ALARM_ID)
        );

        alarmRepository.delete(alarm);
    }
}
