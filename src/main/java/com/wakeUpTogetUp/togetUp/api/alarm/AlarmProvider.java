package com.wakeUpTogetUp.togetUp.api.alarm;

import static com.wakeUpTogetUp.togetUp.common.Constant.GET_ALARM_MODE_GROUP;
import static com.wakeUpTogetUp.togetUp.common.Constant.GET_ALARM_MODE_PERSONAL;

import com.wakeUpTogetUp.togetUp.api.alarm.dto.response.AlarmSimpleRes;
import com.wakeUpTogetUp.togetUp.api.alarm.dto.response.AlarmTimeLineRes;
import com.wakeUpTogetUp.togetUp.api.alarm.dto.response.GetAlarmRes;
import com.wakeUpTogetUp.togetUp.api.alarm.model.Alarm;
import com.wakeUpTogetUp.togetUp.api.users.UserValidationService;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.utils.DateTimeProvider;
import com.wakeUpTogetUp.togetUp.utils.mapper.EntityDtoMapper;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlarmProvider {

    private final UserValidationService userValidationService;
    private final AlarmRepository alarmRepository;

    public List<GetAlarmRes> getAlarmsByUserIdOrderByDate(Integer userId, String type) {
        userValidationService.validateUserExist(userId);

        if (type.equals(GET_ALARM_MODE_PERSONAL)) {
            List<Alarm> alarmList = alarmRepository.findAllByUser_IdAndRoom_IdIsNullOrderByAlarmTime(userId);
            return EntityDtoMapper.INSTANCE.toAlarmResList(alarmList);
        } else if (type.equals(GET_ALARM_MODE_GROUP)) {
            List<Alarm> alarmList = alarmRepository.findRoomAlarmByUserId(userId);
            return EntityDtoMapper.INSTANCE.toAlarmResList(alarmList);
        } else {
            throw new BaseException(Status.BAD_REQUEST_PARAM);
        }
    }

    public GetAlarmRes getAlarmById(Integer alarmId) {
        Alarm alarm = alarmRepository.findById(alarmId)
                .orElseThrow(() -> new BaseException(Status.ALARM_NOT_FOUND));

        return EntityDtoMapper.INSTANCE.toAlarmRes(alarm);
    }

    public AlarmTimeLineRes getAlarmTimeLineByUserId(Integer userId) {
        LocalDate today = DateTimeProvider.getCurrentDateInSeoul();
        String dayOfWeek = today.getDayOfWeek().name();

        List<Alarm> todayAlarms = alarmRepository.findTodayAlarmsByUserId(userId, dayOfWeek);
        AlarmSimpleRes nextAlarm = EntityDtoMapper.INSTANCE.toAlarmSimpleRes(getNextAlarm(todayAlarms));
        List<AlarmSimpleRes> alarmSimpleResList = EntityDtoMapper.INSTANCE.toAlarmSimpleResList(todayAlarms);

        return AlarmTimeLineRes.builder()
                .today(today)
                .dayOfWeek(dayOfWeek)
                .nextAlarm(nextAlarm)
                .todayAlarmList(alarmSimpleResList)
                .build();
    }

    private Alarm getNextAlarm(List<Alarm> alarms) {
        LocalTime now = LocalTime.now();

        return alarms.stream()
                .filter(alarm -> alarm.getAlarmTime().isAfter(now))
                .findFirst()
                .orElse(null);
    }
}
