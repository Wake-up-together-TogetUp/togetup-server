package com.wakeUpTogetUp.togetUp.api.alarm;

import com.wakeUpTogetUp.togetUp.api.alarm.model.Alarm;
import com.wakeUpTogetUp.togetUp.api.mission.model.MissionObject;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Integer> {

    Optional<Alarm> findByIdAndUser_Id(Integer id, Integer userId);

    Optional<Alarm> findByUser_IdAndRoom_Id(@Param("userId") Integer userId, @Param("roomId") Integer roomId);

    @Query("SELECT a.id FROM Alarm a WHERE a.user.id = :userId")
    Set<Integer> findUserAlarmIds(@Param("userId") Integer userId);

    List<Alarm> findAllByUser_IdAndRoom_IdIsNullOrderByAlarmTime(Integer userId);

    @Query("SELECT a "
            + "FROM Alarm a "
            + "WHERE a.room.id IN (SELECT ru.room.id FROM RoomUser ru WHERE ru.user.id = :userId) "
            + "ORDER BY a.alarmTime")
    List<Alarm> findRoomAlarmByUserId(@Param("userId") Integer userId);

    @Query("SELECT a FROM Alarm a "
            + "WHERE a.user.id = :userId "
            + "AND a.id IN (SELECT ml.alarm.id FROM MissionLog ml WHERE CAST(ml.createdAt AS LocalDate) = :today) "
            + "ORDER BY a.alarmTime ASC, a.id ASC")
    List<Alarm> findAllUserAlarmsWithTodayLog(@Param("userId") Integer userId, @Param("today") LocalDate today);

    @Query("SELECT a FROM Alarm a "
            + "WHERE a.user.id = :userId "
            + "AND (((:dayOfWeek = 'MONDAY' AND a.monday = TRUE) OR "
            + "(:dayOfWeek = 'TUESDAY' AND a.tuesday = TRUE) OR "
            + "(:dayOfWeek = 'WEDNESDAY' AND a.wednesday = TRUE) OR "
            + "(:dayOfWeek = 'THURSDAY' AND a.thursday = TRUE) OR "
            + "(:dayOfWeek = 'FRIDAY' AND a.friday = TRUE) OR "
            + "(:dayOfWeek = 'SATURDAY' AND a.saturday = TRUE) OR "
            + "(:dayOfWeek = 'SUNDAY' AND a.sunday = TRUE)) OR "
            + "(a.monday = FALSE AND a.tuesday = FALSE AND a.wednesday = FALSE AND "
            + "a.thursday = FALSE AND a.friday = FALSE AND a.saturday = FALSE AND a.sunday = FALSE)) "
            + "AND a.isActivated = TRUE "
            + "AND a.alarmTime >= :now "
            + "ORDER BY a.alarmTime ASC, a.id ASC")
    List<Alarm> findAllUserTodayActiveAlarmsAfterNow(
            @Param("userId") Integer userId,
            @Param("dayOfWeek") String dayOfWeek,
            @Param("now") LocalTime time);

    @Query("SELECT a.missionObject FROM Alarm a WHERE a.room.id = :roomId ")
    MissionObject findMissionObjectByRoomId(@Param("roomId") Integer roomId);

    @Modifying
    @Query("UPDATE Alarm a SET a.room.id = :roomId WHERE a.id = :alarmId")
    void updateRoomIdByAlarmId(@Param("alarmId") Integer alarmId, @Param("roomId") Integer roomId);

    @Modifying
    @Query("UPDATE Alarm a SET a.isActivated = false WHERE a.id IN :alarmIds")
    void deactivateAlarms(@Param("alarmIds") List<Integer> alarmIds);
}
