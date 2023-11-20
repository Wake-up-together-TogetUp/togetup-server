package com.wakeUpTogetUp.togetUp.api.alarm;

import com.wakeUpTogetUp.togetUp.api.alarm.model.Alarm;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Integer> {
    @Override
    <S extends Alarm> S save(S entity);

    @Override
    Optional<Alarm> findById(Integer integer);

    @Query("select a from Alarm a where a.id = :id and a.user.id = :userId")
    Optional<Alarm> findById(Integer id, Integer userId);

    List<Alarm> findAllByUser_IdAndRoom_IdIsNullOrderByAlarmTime(Integer userId);

    // 그룹 알람 가져오기
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


    @Query("SELECT a FROM Alarm a WHERE a.room.invitationCode = :invitationCode")
    Optional<Alarm> findByInvitationCode(@Param("invitationCode") String invitationCode);

    @Query("SELECT a FROM Alarm a WHERE " +
            "a.user.id = :userId AND " +
            "a.alarmTime >= :alarmTime AND " +
            "(:dayOfWeek = 'MONDAY' AND a.monday = TRUE) OR " +
            "(:dayOfWeek = 'TUESDAY' AND a.tuesday = TRUE) OR " +
            "(:dayOfWeek = 'WEDNESDAY' AND a.wednesday = TRUE) OR " +
            "(:dayOfWeek = 'THURSDAY' AND a.thursday = TRUE) OR " +
            "(:dayOfWeek = 'FRIDAY' AND a.friday = TRUE) OR " +
            "(:dayOfWeek = 'SATURDAY' AND a.saturday = TRUE) OR " +
            "(:dayOfWeek = 'SUNDAY' AND a.sunday = TRUE) " +
            "ORDER BY a.alarmTime ASC, a.id ASC")
    List<Alarm> findNextAlarmsByUserId(@Param("userId") Integer userId,
                                       @Param("dayOfWeek") String dayOfWeek,
                                       @Param("alarmTime") LocalTime alarmTime);
}
