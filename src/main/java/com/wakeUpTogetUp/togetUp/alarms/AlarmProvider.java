package com.wakeUpTogetUp.togetUp.alarms;

import com.wakeUpTogetUp.togetUp.alarms.model.Alarm;
import com.wakeUpTogetUp.togetUp.alarms.model.GetAlarmRes;
import com.wakeUpTogetUp.togetUp.alarms.model.GetAlarmsRes;
import com.wakeUpTogetUp.togetUp.common.BaseException;
import com.wakeUpTogetUp.togetUp.common.BaseResponseStatus;
import com.wakeUpTogetUp.togetUp.routines.RoutineDao;
import com.wakeUpTogetUp.togetUp.routines.model.GetRoutineRes;
import com.wakeUpTogetUp.togetUp.routines.model.Routine;
import com.wakeUpTogetUp.togetUp.utils.mappers.AlarmMapper;
import com.wakeUpTogetUp.togetUp.utils.mappers.RoutineMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmProvider {
    private final AlarmDao alarmDao;
    private final RoutineDao routineDao;

    public List<GetAlarmsRes> getAlarmsByUserId(Integer userId) {
        List<Alarm> alarmList = alarmDao.findByUserId(userId)
                .orElseThrow(
                        () -> new BaseException(BaseResponseStatus.ALARM_NOT_FOUND)
                );

        ArrayList<GetAlarmsRes> getAlarmsResList = new ArrayList<>();
        for(Alarm alarm : alarmList) {
            getAlarmsResList.add(AlarmMapper.INSTANCE.entityToGetAlarmsRes(alarm));
        }

        return getAlarmsResList;
    }

    public GetAlarmRes getAlarm(Integer alarmId) {
        // 알람 가져오기
        Alarm alarm = alarmDao.findById(alarmId)
                .orElseThrow(
                        () -> new BaseException(BaseResponseStatus.INVALID_ALARM_ID)
                );

        // 루틴 리스트 가져오기
        // 하나도 존재하지 않아도 됨
        List<Routine> routineList = routineDao.findByAlarmId(alarmId).orElseGet(() -> null);

        ArrayList<GetRoutineRes> getRoutineResList = new ArrayList<>();
        for(Routine routine : routineList) {
            getRoutineResList.add(RoutineMapper.INSTANCE.entityToGetRoutineRes(routine));
        }

        GetAlarmRes getAlarmRes = AlarmMapper.INSTANCE.entityToGetAlarmRes(alarm, getRoutineResList);
        
        return getAlarmRes;
    }
}
