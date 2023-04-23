package com.wakeUpTogetUp.togetUp.alarms;

import com.wakeUpTogetUp.togetUp.alarms.model.GetAlarmsRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmProvider {
    private final AlarmDao alarmDao;

//    public List<GetAlarmsRes> getAlarmsByUserId(Long userId) {
//        return alarmDao.findByUserId(userId);
//    }
}
