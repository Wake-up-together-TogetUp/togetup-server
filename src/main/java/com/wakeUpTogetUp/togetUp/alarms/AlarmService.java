package com.wakeUpTogetUp.togetUp.alarms;

import com.wakeUpTogetUp.togetUp.alarms.dto.request.PatchAlarmReq;
import com.wakeUpTogetUp.togetUp.alarms.dto.response.AlarmRes;
import com.wakeUpTogetUp.togetUp.alarms.model.Alarm;
import com.wakeUpTogetUp.togetUp.mappingAlarmRoutine.model.MappingAlarmRoutine;
import com.wakeUpTogetUp.togetUp.alarms.dto.request.PostAlarmReq;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.mappingAlarmRoutine.MappingAlarmRoutineRepository;
import com.wakeUpTogetUp.togetUp.missions.MissionRepository;
import com.wakeUpTogetUp.togetUp.missions.model.Mission;
import com.wakeUpTogetUp.togetUp.routines.RoutineProvider;
import com.wakeUpTogetUp.togetUp.routines.RoutineRepository;
import com.wakeUpTogetUp.togetUp.routines.model.Routine;
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
    private final AlarmRepository alarmRepository;
    private final RoutineRepository routineRepository;
    private final UserRepository userRepository;
    private final MappingAlarmRoutineRepository mappingAlarmRoutineRepository;
    private final MissionRepository missionRepository;

    // 알람 생성
    @Transactional
    public AlarmRes createAlarm(Integer userId, PostAlarmReq postAlarmReq) {
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
                .startHour(postAlarmReq.getStartHour())
                .startMinute(postAlarmReq.getStartMinute())
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
        if(!Objects.isNull(postAlarmReq.getRoutineIdList())) {
            // 매핑 알람 루틴 생성
            createMappingAlarmRoutineList(postAlarmReq, alarm);
            alarmRes.setRoutineResList(routineProvider.getRoutineResByAlarmId(alarmCreated.getId()));
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
                patchAlarmReq.getStartHour(),
                patchAlarmReq.getStartMinute(),
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

        // 루틴 리스트 수정
        // 연관된 매핑 루틴 리스트 삭제
        mappingAlarmRoutineRepository.deleteByAlarmId(alarmId);

        // 매핑 루틴 리스트 재생성
        if(!Objects.isNull(patchAlarmReq.getRoutineIdList())){
            createMappingAlarmRoutineList(patchAlarmReq, alarmModified);
            // routine response 리스트 가져오기
            alarmRes.setRoutineResList(routineProvider.getRoutineResByAlarmId(alarmModified.getId()));
        }
        // return
        return alarmRes;
    }

    // 알람 삭제
    @Transactional
    public void deleteAlarm(Integer alarmId) {
        // 해당 아이디의 알람이 존재하는지 확인
        Alarm alarm = alarmRepository.findById(alarmId).orElseThrow(
                () -> new BaseException(Status.INVALID_ALARM_ID)
        );

        alarmRepository.delete(alarm);
    }

    // 매핑 알람 루틴 리스트 생성하기 - 생성
    @Transactional
    protected void createMappingAlarmRoutineList(PostAlarmReq postAlarmReq, Alarm alarm){
        // TODO : 어떻게든 바꿔보셈
        int i=1;

        // 매핑 알람-루틴 생성
        for(Integer routineId : postAlarmReq.getRoutineIdList()) {
            Routine routine = routineRepository.findById(routineId)
                    .orElseThrow(
                            () -> new BaseException(Status.INVALID_ROUTINE_ID)
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
                            () -> new BaseException(Status.INVALID_ROUTINE_ID)
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
}
