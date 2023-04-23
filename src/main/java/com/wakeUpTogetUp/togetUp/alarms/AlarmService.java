package com.wakeUpTogetUp.togetUp.alarms;

import com.wakeUpTogetUp.togetUp.alarms.model.Alarm;
import com.wakeUpTogetUp.togetUp.alarms.model.PostAlarmReq;
import com.wakeUpTogetUp.togetUp.common.BaseException;
import com.wakeUpTogetUp.togetUp.common.BaseResponseStatus;
import com.wakeUpTogetUp.togetUp.users.UserDao;
import com.wakeUpTogetUp.togetUp.users.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AlarmService {
    private final AlarmDao alarmDao;
    private final UserDao userDao;
    @Transactional
    public Long createAlarm(int userId, PostAlarmReq postAlarmReq) {
        UserEntity user = userDao.findById(userId).orElseThrow(
                () -> new BaseException(BaseResponseStatus.ALARM_INVALID_USER_ID)
        );

        Alarm alarm = Alarm.builder()
                .user(user)
                .name(postAlarmReq.getName())
                .icon(postAlarmReq.getIcon())
                .sound(postAlarmReq.getSound())
                .volume(postAlarmReq.getVolume())
                .isEnabled(postAlarmReq.getIsEnabled())
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

        return alarm.getId();
    }
}
