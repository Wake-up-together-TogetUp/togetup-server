package com.wakeUpTogetUp.togetUp.routines;

import com.wakeUpTogetUp.togetUp.alarms.model.Alarm;
import com.wakeUpTogetUp.togetUp.common.interfaces.CustomRepository;
import com.wakeUpTogetUp.togetUp.routines.model.Routine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoutineDao implements CustomRepository<Routine> {
    private final EntityManager em;
    @Override
    public void save(Routine routine) {
        em.persist(routine);
    }

    @Override
    public Optional<Routine> findById(Integer id) {
        return Optional.ofNullable(em.find(Routine.class, id));
    }

    public Optional<List<Routine>> findByAlarmId(Integer alarmId) {
        List<Routine> routineList = em.createQuery(
                "select r from Routine r " +
                        "join MappingAlarmRoutine mar on (r.id = mar.routine.id) " +
                        "where mar.alarm.id = :alarmId", Routine.class)
                .setParameter("alarmId", alarmId)
                .getResultList();

        return Optional.ofNullable(routineList);
    }

    public Optional<List<Routine>> findByUserId(Integer userId) {
        List<Routine> routineList = em.createQuery(
                        "select r from Routine r " +
                                "where r.user.id = :userId",
                        Routine.class)
                .setParameter("userId", userId)
                .getResultList();

        return Optional.ofNullable(routineList);
    }
}