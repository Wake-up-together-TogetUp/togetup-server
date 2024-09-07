package com.wakeUpTogetUp.togetUp.api.alarm.service;

import com.wakeUpTogetUp.togetUp.api.alarm.controller.dto.response.AlarmDetailRes;
import com.wakeUpTogetUp.togetUp.api.alarm.controller.dto.response.AlarmSimpleRes;
import com.wakeUpTogetUp.togetUp.api.alarm.controller.dto.response.AlarmTimeLineRes;
import com.wakeUpTogetUp.togetUp.api.alarm.domain.AlarmType;
import com.wakeUpTogetUp.togetUp.api.alarm.model.Alarm;
import com.wakeUpTogetUp.togetUp.api.alarm.repository.AlarmRepository;
import com.wakeUpTogetUp.togetUp.api.users.UserValidationService;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.common.annotation.LogExecutionTime;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.utils.mapper.EntityDtoMapper;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlarmQueryService {

    private final UserValidationService userValidationService;
    private final AlarmRepository alarmRepository;

    public AlarmDetailRes getAlarmById(Integer alarmId) {
        Alarm alarm = alarmRepository.findById(alarmId)
                .orElseThrow(() -> new BaseException(Status.ALARM_NOT_FOUND));

        return EntityDtoMapper.INSTANCE.toAlarmDetailRes(alarm);
    }

    public List<AlarmDetailRes> getAlarmsByUserIdOrderByDate(Integer userId, String type) {
        userValidationService.validateUserExist(userId);

        List<Alarm> alarms;
        switch (AlarmType.valueOf(type)) {
            case PERSONAL:
                alarms = alarmRepository.findAllByUser_IdAndRoom_IdIsNullOrderByAlarmTime(userId);
                break;
            case GROUP:
                alarms = alarmRepository.findRoomAlarmByUserId(userId);
                break;
            default:
                throw new BaseException(Status.BAD_REQUEST_PARAM);
        }

        return EntityDtoMapper.INSTANCE.toAlarmDetailResList(alarms);
    }

    @LogExecutionTime
    public AlarmTimeLineRes getTimeLine(Integer userId, LocalDateTime now) {
        LocalTime time = now.toLocalTime();

        List<AlarmSimpleRes> timeline = getMergedTimeLine(userId, now);
        AlarmSimpleRes nextAlarmRes = getNextAlarmSimpleRes(timeline, time)
                .orElse(null);

        return AlarmTimeLineRes.builder()
                .today(now.toLocalDate())
                .dayOfWeek(now.getDayOfWeek())
                .nextAlarm(nextAlarmRes)
                .todayAlarmList(timeline)
                .build();
    }

    @LogExecutionTime
    private List<AlarmSimpleRes> getMergedTimeLine(Integer userId, LocalDateTime now) {
        List<AlarmSimpleRes> alarmsWithTodayLog =
                alarmRepository.findAllUserAlarmsWithTodayLog(userId, now.toLocalDate().atStartOfDay());
        List<AlarmSimpleRes> alarmsActiveAfterNow =
                alarmRepository.findAllUserTodayActiveAlarmsAfterNow(userId, now);

        return Stream.concat(
                        alarmsWithTodayLog.stream(),
                        alarmsActiveAfterNow.stream())
                .collect(Collectors.toList());
    }

    private Optional<AlarmSimpleRes> getNextAlarmSimpleRes(List<AlarmSimpleRes> timeLine, LocalTime now) {
        return timeLine.stream()
                .filter(alarm -> alarm.getAlarmTime().isAfter(now))
                .findFirst();
    }
}
