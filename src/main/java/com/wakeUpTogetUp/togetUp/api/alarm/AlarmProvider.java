package com.wakeUpTogetUp.togetUp.api.alarm;

import com.wakeUpTogetUp.togetUp.api.alarm.dto.response.GetAlarmRes;
import com.wakeUpTogetUp.togetUp.api.alarm.model.Alarm;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.api.users.UserRepository;
import com.wakeUpTogetUp.togetUp.utils.mapper.EntityDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public List<GetAlarmRes> getAlarmsByUserId(Integer userId) {
        // 유저 id 유무 확인
        userRepository.findById(userId).orElseThrow(
                () -> new BaseException(Status.USER_NOT_FOUND));

        List<Alarm> alarmList = alarmRepository.findByUserId(userId);

        return EntityDtoMapper.INSTANCE.toAlarmResList(alarmList);
    }

    /**
     * 알람 아이디로 알람 가져오기
     * @param alarmId
     * @return
     */
    public GetAlarmRes getAlarm(Integer alarmId) {
        // 알람 가져오기
        Alarm alarm = alarmRepository.findById(alarmId)
                .orElseThrow(
                        () -> new BaseException(Status.ALARM_NOT_FOUND));

        return EntityDtoMapper.INSTANCE.toAlarmRes(alarm);
    }
}
