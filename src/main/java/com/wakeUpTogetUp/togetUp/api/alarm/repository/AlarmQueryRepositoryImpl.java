package com.wakeUpTogetUp.togetUp.api.alarm.repository;

import static com.wakeUpTogetUp.togetUp.api.alarm.model.QAlarm.alarm;
import static com.wakeUpTogetUp.togetUp.api.mission.model.QMissionObject.missionObject;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wakeUpTogetUp.togetUp.api.alarm.controller.dto.response.AlarmSimpleRes;
import com.wakeUpTogetUp.togetUp.api.alarm.controller.dto.response.QAlarmSimpleRes;
import com.wakeUpTogetUp.togetUp.api.alarm.domain.AlarmType;
import com.wakeUpTogetUp.togetUp.api.alarm.model.Alarm;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.common.annotation.LogExecutionTime;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AlarmQueryRepositoryImpl implements AlarmQueryRepository {

    private final JPAQueryFactory query;

    @Override
    @LogExecutionTime
    public List<AlarmSimpleRes> findAllUserTodayActiveAlarmsAfterNow(Integer userId, LocalDateTime now) {
        return query
                .select(
                        new QAlarmSimpleRes(
                                alarm.id,
                                    missionObject.icon,
                                alarm.alarmTime,
                                alarm.name,
                                missionObject.kr,
                                alarm.room.id
                        )
                )
                .from(alarm)
                .join(alarm.missionObject, missionObject)
                .where(
                        alarm.user.id.eq(userId)
                                .and(alarm.isDeleted.eq(false))
                                .and(alarm.isActivated.eq(true))
                                .and(alarmTimeGoe(now.toLocalTime()))
                                .and(isDayOrOneTimeAlarm(now))
                )
                .orderBy(
                        alarm.alarmTime.asc(),
                        alarm.id.asc()
                )
                .fetch();
    }

    @Override
    public List<Alarm> findUserAlarmsByType(Integer userId, AlarmType type) {
        return query.selectFrom(alarm)
                .where(
                        alarm.user.id.eq(userId),
                        alarm.isDeleted.eq(false),
                        getAlarmTypeCondition(type)
                )
                .orderBy(
                        alarm.alarmTime.asc()
                )
                .fetch();
    }

    private BooleanExpression alarmTimeGoe(LocalTime now) {
        return alarm.alarmTime.goe(now);
    }

    @LogExecutionTime
    private BooleanExpression isDayOrOneTimeAlarm(LocalDateTime now) {
        return isDayAlarm(now.getDayOfWeek())
                .or(isOneTimeAlarm());
    }

    private BooleanExpression isDayAlarm(DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY:
                return alarm.monday.eq(true);
            case TUESDAY:
                return alarm.tuesday.eq(true);
            case WEDNESDAY:
                return alarm.wednesday.eq(true);
            case THURSDAY:
                return alarm.thursday.eq(true);
            case FRIDAY:
                return alarm.friday.eq(true);
            case SATURDAY:
                return alarm.saturday.eq(true);
            case SUNDAY:
                return alarm.sunday.eq(true);
            default:
                return Expressions.asBoolean(false);
        }
    }

    private BooleanExpression isOneTimeAlarm() {
        return alarm.monday.eq(false)
                .and(alarm.tuesday.eq(false))
                .and(alarm.wednesday.eq(false))
                .and(alarm.thursday.eq(false))
                .and(alarm.friday.eq(false))
                .and(alarm.saturday.eq(false))
                .and(alarm.sunday.eq(false));
    }

    private BooleanExpression getAlarmTypeCondition(AlarmType type) {
        switch (type) {
            case PERSONAL:
                return alarm.room.isNull();
            case GROUP:
                return alarm.room.isNotNull();
            default:
                throw new BaseException(Status.BAD_REQUEST_PARAM);
        }
    }
}
