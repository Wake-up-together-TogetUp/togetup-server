package com.wakeUpTogetUp.togetUp.alarms;

import com.wakeUpTogetUp.togetUp.alarms.model.Alarm;
import com.wakeUpTogetUp.togetUp.alarms.model.MappingAlarmRoutine;
import com.wakeUpTogetUp.togetUp.alarms.model.PostAlarmReq;
import com.wakeUpTogetUp.togetUp.common.BaseException;
import com.wakeUpTogetUp.togetUp.common.BaseResponseStatus;
import com.wakeUpTogetUp.togetUp.routines.RoutineDao;
import com.wakeUpTogetUp.togetUp.routines.model.Routine;
import com.wakeUpTogetUp.togetUp.users.UserRepository;
import com.wakeUpTogetUp.togetUp.users.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AlarmService {
    private final AlarmDao alarmDao;
    private final RoutineDao routineDao;
    private final UserRepository userRepository;
    @Transactional
    // TODO : create Alarm 테스트해봐야함
    public int createAlarm(int userId, PostAlarmReq postAlarmReq) {
        User user = userRepository.findById(userId)
                .orElseThrow(
                () -> new BaseException(BaseResponseStatus.INVALID_USER_ID)
        );

        Alarm alarm = Alarm.builder()
                .user(user)
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

        alarmDao.save(alarm);

        int alarmId = alarm.getId();
        int i=1;

        // 루틴 리스트가 있으면
        if(postAlarmReq.getRoutineIdList() != null) {
            // 매핑 알람-루틴 생성
            for(Integer routineId : postAlarmReq.getRoutineIdList()) {
                Routine routine = routineDao.findById(routineId)
                        .orElseThrow(
                                () -> new BaseException(BaseResponseStatus.INVALID_ROUTINE_ID)
                        );
                MappingAlarmRoutine mappingAlarmRoutine = MappingAlarmRoutine.builder()
                        .user(user)
                        .alarm(alarm)
                        .routine(routine)
                        .order(i)
                        .build();
                alarmDao.save(mappingAlarmRoutine);
                i++;
            }
        }
        return alarmId;
    }
}
