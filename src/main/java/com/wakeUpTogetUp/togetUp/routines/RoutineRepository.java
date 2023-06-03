package com.wakeUpTogetUp.togetUp.routines;

import com.wakeUpTogetUp.togetUp.routines.model.Routine;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoutineRepository extends JpaRepository<Routine, Integer> {
    @Override
    Optional<Routine> findById(Integer id);

    @Query("SELECT r from Routine r where r.alarm.id = :alarmId")
    List<Routine> findAllByAlarmId(@Param(value = "alarmId") Integer alarmId, Sort sort);
}