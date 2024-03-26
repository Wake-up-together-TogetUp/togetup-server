package com.wakeUpTogetUp.togetUp.api.alarm;

import com.wakeUpTogetUp.togetUp.api.alarm.model.Alarm;

import java.util.List;
import java.util.Optional;

import com.wakeUpTogetUp.togetUp.api.mission.model.MissionObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Integer> {
    @Query("select a from Alarm a where a.id = :id and a.user.id = :userId")
    Optional<Alarm> findById(Integer id, Integer userId);

    List<Alarm> findAllByUser_IdAndRoom_IdIsNullOrderByAlarmTime(Integer userId);

    @Query("SELECT a "
            + "FROM Alarm a "
            + "WHERE a.room.id IN (SELECT ru.room.id FROM RoomUser ru WHERE ru.user.id = :userId) "
            + "ORDER BY a.alarmTime")
    List<Alarm> findRoomAlarmByUserId(@Param("userId") Integer userId);

    @Modifying
    @Query("update Alarm a set a.room.id = :roomId where a.id = :alarmId")
    void updateRoomIdByAlarmId(@Param("alarmId") Integer alarmId, @Param("roomId") Integer roomId);

    Alarm findFirstByRoom_Id(Integer roomId);



    @Query("select a from Alarm a where a.room.id IN :roomIds")
            List<Alarm> findAllByRoomIds(@Param("roomIds") List<Integer> roomIds);

    Optional<Alarm> findByUser_IdAndRoom_Id(@Param("userId") Integer userId, @Param("roomId") Integer roomId);

    @Query("SELECT a FROM Alarm a " +
            "WHERE (a.user.id = :userId OR a.room.id IN (" +
            "SELECT ru.id FROM RoomUser ru WHERE ru.user.id = :userId)) " +
            "AND ((:dayOfWeek = 'MONDAY' AND a.monday = TRUE) OR " +
            "(:dayOfWeek = 'TUESDAY' AND a.tuesday = TRUE) OR " +
            "(:dayOfWeek = 'WEDNESDAY' AND a.wednesday = TRUE) OR " +
            "(:dayOfWeek = 'THURSDAY' AND a.thursday = TRUE) OR " +
            "(:dayOfWeek = 'FRIDAY' AND a.friday = TRUE) OR " +
            "(:dayOfWeek = 'SATURDAY' AND a.saturday = TRUE) OR " +
            "(:dayOfWeek = 'SUNDAY' AND a.sunday = TRUE)) " +
            "AND a.isActivated = TRUE " +
            "ORDER BY a.alarmTime ASC, a.id ASC")
    List<Alarm> findTodayAlarmsByUserId(@Param("userId") Integer userId,
                                        @Param("dayOfWeek") String dayOfWeek);


    @Query("SELECT a.missionObject FROM Alarm a WHERE a.room.id = :roomId ")
    MissionObject findMissionObjectByRoomId(@Param("roomId") Integer roomId);
}
