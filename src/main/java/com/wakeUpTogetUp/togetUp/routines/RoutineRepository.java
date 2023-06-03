package com.wakeUpTogetUp.togetUp.routines;

import com.wakeUpTogetUp.togetUp.routines.model.Routine;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoutineRepository extends JpaRepository<Routine, Integer> {
    @Override
    Optional<Routine> findById(Integer id);

    @Query("SELECT r FROM Routine r WHERE r.alarm.id = :alarmId")
    List<Routine> findAllByAlarmId(@Param(value = "alarmId") int alarmId, Sort sort);

    @Modifying
    @Query("DELETE FROM Routine r WHERE r.alarm.id = :alarmId")
    void deleteAllByAlarmId(@Param(value = "alarmId") int alarmId);
}