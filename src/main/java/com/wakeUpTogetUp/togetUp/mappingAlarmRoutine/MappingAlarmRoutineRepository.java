package com.wakeUpTogetUp.togetUp.mappingAlarmRoutine;

import com.wakeUpTogetUp.togetUp.mappingAlarmRoutine.model.MappingAlarmRoutine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MappingAlarmRoutineRepository extends JpaRepository<MappingAlarmRoutine, Integer> {
    @Override
    <S extends MappingAlarmRoutine> S save(S entity);

    // 알람 아이디로 삭제
    @Modifying
    @Query("DELETE FROM MappingAlarmRoutine mar where mar.alarm.id = :alarmId")
    void deleteByAlarmId(@Param(value = "alarmId") Integer alarmId);
}
