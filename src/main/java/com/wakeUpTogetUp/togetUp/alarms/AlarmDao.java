package com.wakeUpTogetUp.togetUp.alarms;

import com.wakeUpTogetUp.togetUp.alarms.model.Alarm;
import com.wakeUpTogetUp.togetUp.alarms.model.GetAlarmRes;
import com.wakeUpTogetUp.togetUp.alarms.model.GetAlarmsRes;
import com.wakeUpTogetUp.togetUp.alarms.model.MappingAlarmRoutine;
import com.wakeUpTogetUp.togetUp.common.interfaces.CustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AlarmDao implements CustomRepository<Alarm> {
    private final EntityManager em;

    // 알람 저장
    @Override
    public void save(Alarm alarm) {
        em.persist(alarm);
    }

    // 매핑 알람-루틴 저장
    public void save(MappingAlarmRoutine mappingAlarmRoutine) {
        em.persist(mappingAlarmRoutine);
    }

    @Override
    public Optional<Alarm> findById(Integer id) {
        Alarm alarm = em.find(Alarm.class, id);
        return Optional.ofNullable(alarm);
    }

    public Optional<List<Alarm>> findByUserId(Integer userId) {
        List<Alarm> alarmList = em.createQuery("select a from Alarm a where a.user.id = :userId", Alarm.class)
                .setParameter("userId", userId)
                .getResultList();

        return Optional.ofNullable(alarmList);
    }
}
