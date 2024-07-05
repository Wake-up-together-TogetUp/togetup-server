package com.wakeUpTogetUp.togetUp.api.alarm.service;

import com.wakeUpTogetUp.togetUp.api.alarm.repository.AlarmRepository;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlarmValidationService {

    private final AlarmRepository alarmRepository;

    public void validateUserAlarms(Integer userId, List<Integer> alarmIds) {
        Set<Integer> userAlarmIds = alarmRepository.findUserAlarmIds(userId);
        boolean allMatch = userAlarmIds.containsAll(alarmIds);

        if (!allMatch) {
            throw new BaseException(Status.USER_ALARM_ACCESS_DENIED);
        }
    }
}
