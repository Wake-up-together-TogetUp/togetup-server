package com.wakeUpTogetUp.togetUp.alarm;

import com.wakeUpTogetUp.togetUp.alarm.model.Alarm;
import com.wakeUpTogetUp.togetUp.alarm.dto.response.AlarmRes;
import com.wakeUpTogetUp.togetUp.alarm.dto.response.AlarmsRes;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.users.UserRepository;
import com.wakeUpTogetUp.togetUp.utils.mapper.EntityDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmProvider {
    private final UserRepository userRepository;
    private final AlarmRepository alarmRepository;

    /**
     * 유저 아이디로 알람 리스트 가져오기
     * @param userId
     * @return
     */
    public List<AlarmsRes> getAlarmsByUserId(Integer userId) {
        // 유저 id 유무 확인
        userRepository.findById(userId).orElseThrow(
                () -> new BaseException(Status.INVALID_USER_ID)
        );

        List<Alarm> alarmList = alarmRepository.findByUserId(userId);

        // dto 매핑
        ArrayList<AlarmsRes> alarmsResList = new ArrayList<>();
        for(Alarm alarm : alarmList) {
            alarmsResList.add(EntityDtoMapper.INSTANCE.toAlarmsRes(alarm));
        }

        return alarmsResList;
    }

    /**
     * 알람 아이디로 알람 가져오기
     * @param alarmId
     * @return
     */
    public AlarmRes getAlarm(Integer alarmId) {
        // 알람 가져오기
        Alarm alarm = alarmRepository.findById(alarmId)
                .orElseThrow(
                        () -> new BaseException(Status.INVALID_ALARM_ID)
                );


        AlarmRes alarmRes = EntityDtoMapper.INSTANCE.toAlarmRes(alarm);

        return alarmRes;
    }
}
