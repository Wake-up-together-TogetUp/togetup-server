package com.wakeUpTogetUp.togetUp.api.alarm.repository;

import static com.wakeUpTogetUp.togetUp.api.alarm.model.QAlarm.alarm;
import static com.wakeUpTogetUp.togetUp.api.mission.model.QMissionObject.missionObject;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wakeUpTogetUp.togetUp.api.alarm.dto.response.AlarmSimpleRes;
import com.wakeUpTogetUp.togetUp.api.alarm.dto.response.QAlarmSimpleRes;
import com.wakeUpTogetUp.togetUp.common.annotation.LogExecutionTime;
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
                                alarm.missionObject.icon,
                                alarm.alarmTime,
                                alarm.name,
                                missionObject.kr,
                                alarm.room.id
                        )
                )
                .from(alarm)
                .innerJoin(missionObject).on(alarm.missionObject.id.eq(missionObject.id))
                .where(
                        alarm.user.id.eq(userId)
                                .and(isDayOrOneTimeAlarm(now))
                )
                .orderBy(
                        alarm.alarmTime.asc(),
                        alarm.id.asc()
                )
                .fetch();
    }


    private BooleanExpression alarmTimeGoe(LocalTime now) {
        return alarm.alarmTime.goe(now);
    }

    @LogExecutionTime
    private BooleanExpression isDayOrOneTimeAlarm(LocalDateTime now) {
        return isDayAlarm(now.getDayOfWeek())
                .or(isOneTimeAlarm())
                .and(isActiveAlarm(now.toLocalTime()));
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

    private BooleanExpression isActiveAlarm(LocalTime now) {
        return alarm.isActivated.eq(true)
                .and(alarm.isDeleted.eq(false))
                .and(alarmTimeGoe(now));
    }
}
