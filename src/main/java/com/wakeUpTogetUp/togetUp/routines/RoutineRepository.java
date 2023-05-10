package com.wakeUpTogetUp.togetUp.routines;

import com.wakeUpTogetUp.togetUp.routines.model.Routine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoutineRepository extends JpaRepository<Routine, Integer> {
    @Override
    <S extends Routine> S save(S entity);

    @Override
    Optional<Routine> findById(Integer id);

    @Query("SELECT r FROM Routine r JOIN MappingAlarmRoutine mar ON (r.id = mar.routine.id) WHERE mar.alarm.id = :alarmId")
    Optional<List<Routine>> findByAlarmId(@Param(value = "alarmId") Integer alarmId);

    @Query("select r from Routine r where r.user.id = :userId")
    Optional<List<Routine>> findByUserId(@Param(value = "userId") Integer userId);
}