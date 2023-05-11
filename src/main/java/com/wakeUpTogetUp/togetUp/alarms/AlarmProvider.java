package com.wakeUpTogetUp.togetUp.alarms;

import com.wakeUpTogetUp.togetUp.alarms.model.Alarm;
import com.wakeUpTogetUp.togetUp.alarms.dto.response.AlarmRes;
import com.wakeUpTogetUp.togetUp.alarms.dto.response.AlarmsRes;
import com.wakeUpTogetUp.togetUp.common.exception.BaseException;
import com.wakeUpTogetUp.togetUp.common.ResponseStatus;
import com.wakeUpTogetUp.togetUp.routines.RoutineRepository;
import com.wakeUpTogetUp.togetUp.routines.dto.response.RoutineRes;
import com.wakeUpTogetUp.togetUp.routines.model.Routine;
import com.wakeUpTogetUp.togetUp.users.UserRepository;
import com.wakeUpTogetUp.togetUp.users.model.User;
import com.wakeUpTogetUp.togetUp.utils.mappers.AlarmMapper;
import com.wakeUpTogetUp.togetUp.utils.mappers.RoutineMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmProvider {
    private final UserRepository userRepository;
    private final AlarmRepository alarmRepository;
    private final RoutineRepository routineRepository;

    /**
     * 유저 아이디로 알람 리스트 가져오기
     * @param userId
     * @return
     */
    public List<AlarmsRes> getAlarmsByUserId(Integer userId) {
        // 유저 id 유무 확인
        User user = userRepository.findById(userId).orElseThrow(
                () -> new BaseException(ResponseStatus.INVALID_USER_ID)
        );

        List<Alarm> alarmList = alarmRepository.findByUserId(userId);

        // dto 매핑
        ArrayList<AlarmsRes> alarmsResList = new ArrayList<>();
        for(Alarm alarm : alarmList) {
            alarmsResList.add(AlarmMapper.INSTANCE.toAlarmsRes(alarm));
        }

        return alarmsResList;
    }

    /**
     * 알람 아이디로 알람 가져오기
     * @param alarmId
     * @return
     */
    public AlarmRes getAlarm(Integer alarmId, Integer userId) {
        // 알람 가져오기
        Alarm alarm = alarmRepository.findById(alarmId, userId)
                .orElseThrow(
                        () -> new BaseException(ResponseStatus.INVALID_ALARM_ID)
                );

        // 루틴 리스트 가져오기
        // 하나도 존재하지 않아도 됨
        List<RoutineRes> routineResList = getRoutineResByAlarmId(alarmId);

        AlarmRes alarmRes = AlarmMapper.INSTANCE.toAlarmRes(alarm, routineResList);

        return alarmRes;
    }

    /**
     * alarmId로 routineRes 리스트 가져오기
     * @param alarmId
     * @return
     */
    public List<RoutineRes> getRoutineResByAlarmId(Integer alarmId){
        List<Routine> routineList = routineRepository.findByAlarmId(alarmId).orElseGet(() -> null);

        ArrayList<RoutineRes> routineResList = new ArrayList<>();
        for(Routine routine : routineList) {
            routineResList.add(RoutineMapper.INSTANCE.toRoutineRes(routine));
        }

        return routineResList;
    }
}
