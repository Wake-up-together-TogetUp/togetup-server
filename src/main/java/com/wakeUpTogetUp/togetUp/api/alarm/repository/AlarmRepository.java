package com.wakeUpTogetUp.togetUp.api.alarm.repository;

import com.wakeUpTogetUp.togetUp.api.alarm.dto.response.AlarmSimpleRes;
import com.wakeUpTogetUp.togetUp.api.alarm.model.Alarm;
import com.wakeUpTogetUp.togetUp.api.mission.model.MissionObject;
import com.wakeUpTogetUp.togetUp.common.annotation.LogExecutionTime;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Integer>, AlarmQueryRepository {
    Optional<Alarm> findByIdAndUser_Id(Integer id, Integer userId);

    Optional<Alarm> findByUser_IdAndRoom_Id(@Param("userId") Integer userId, @Param("roomId") Integer roomId);

    @Query("SELECT a.id FROM Alarm a WHERE a.user.id = :userId")
    Set<Integer> findUserAlarmIds(@Param("userId") Integer userId);

    List<Alarm> findAllByUser_IdAndRoom_IdIsNullOrderByAlarmTime(Integer userId);

    @Query("SELECT a "
            + "FROM Alarm a "
            + "WHERE a.user.id = :userId "
            + "AND a.room.id != null "
            + "ORDER BY a.alarmTime")
    List<Alarm> findRoomAlarmByUserId(@Param("userId") Integer userId);

    @LogExecutionTime
    @Query("SELECT new com.wakeUpTogetUp.togetUp.api.alarm.dto.response.AlarmSimpleRes("
            + "a.id, "
            + "a.missionObject.icon, "
            + "a.alarmTime, "
            + "a.name, "
            + "mo.kr, "
            + "a.room.id) "
            + "FROM Alarm a "
            + "JOIN MissionObject mo ON mo.id = a.missionObject.id "
            + "JOIN MissionLog ml ON a.id = ml.alarm.id "
            + "WHERE a.user.id = :userId "
            + "AND  ml.createdAt >= :baseTime "
            + "ORDER BY a.alarmTime ASC, a.id ASC")
    List<AlarmSimpleRes> findAllUserAlarmsWithTodayLog(@Param("userId") Integer userId, @Param("baseTime") LocalDateTime baseTime);

    @Query("SELECT a.missionObject FROM Alarm a WHERE a.room.id = :roomId ")
    MissionObject findMissionObjectByRoomId(@Param("roomId") Integer roomId);

    @Modifying
    @Query("UPDATE Alarm a SET a.room.id = :roomId WHERE a.id = :alarmId")
    void updateRoomIdByAlarmId(@Param("alarmId") Integer alarmId, @Param("roomId") Integer roomId);

    @Modifying
    @Query("UPDATE Alarm a SET a.isActivated = false WHERE a.id IN :alarmIds")
    void deactivateAlarms(@Param("alarmIds") List<Integer> alarmIds);
}
