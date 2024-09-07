package com.wakeUpTogetUp.togetUp.api.alarm.service;

import static com.wakeUpTogetUp.togetUp.common.Constant.GET_ALARM_MODE_GROUP;
import static com.wakeUpTogetUp.togetUp.common.Constant.GET_ALARM_MODE_PERSONAL;

import com.wakeUpTogetUp.togetUp.api.alarm.controller.dto.response.AlarmDetailRes;
import com.wakeUpTogetUp.togetUp.api.alarm.controller.dto.response.AlarmSimpleRes;
import com.wakeUpTogetUp.togetUp.api.alarm.controller.dto.response.AlarmTimeLineRes;
import com.wakeUpTogetUp.togetUp.api.alarm.model.Alarm;
import com.wakeUpTogetUp.togetUp.api.alarm.repository.AlarmRepository;
import com.wakeUpTogetUp.togetUp.api.users.UserValidationService;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.common.annotation.LogExecutionTime;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.utils.mapper.EntityDtoMapper;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlarmQueryService {

    private final UserValidationService userValidationService;
    private final AlarmRepository alarmRepository;

    public List<AlarmDetailRes> getAlarmsByUserIdOrderByDate(Integer userId, String type) {
        userValidationService.validateUserExist(userId);

        List<Alarm> alarms;
        if (type.equals(GET_ALARM_MODE_PERSONAL)) {
            alarms = alarmRepository.findAllByUser_IdAndRoom_IdIsNullOrderByAlarmTime(userId);
        } else if (type.equals(GET_ALARM_MODE_GROUP)) {
            alarms = alarmRepository.findRoomAlarmByUserId(userId);
        } else {
            throw new BaseException(Status.BAD_REQUEST_PARAM);
        }

        return EntityDtoMapper.INSTANCE.toAlarmDetailResList(alarms);
    }

    public AlarmDetailRes getAlarmById(Integer alarmId) {
        Alarm alarm = alarmRepository.findById(alarmId)
                .orElseThrow(() -> new BaseException(Status.ALARM_NOT_FOUND));

        return EntityDtoMapper.INSTANCE.toAlarmDetailRes(alarm);
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
        List<AlarmSimpleRes> alarmsWithTodayLog = alarmRepository.findAllUserAlarmsWithTodayLog(userId, now.toLocalDate().atStartOfDay());
        List<AlarmSimpleRes> alarmsActiveAfterNow = alarmRepository.findAllUserTodayActiveAlarmsAfterNow(userId, now);

        List<AlarmSimpleRes> timeline = new ArrayList<>(alarmsWithTodayLog);
        timeline.addAll(alarmsActiveAfterNow);

        return timeline;
    }

    private Optional<AlarmSimpleRes> getNextAlarmSimpleRes(List<AlarmSimpleRes> timeLine, LocalTime now) {
        return timeLine.stream()
                .filter(alarm -> alarm.getAlarmTime().isAfter(now))
                .findFirst();
    }
}
