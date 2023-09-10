package com.wakeUpTogetUp.togetUp.api.alarm;

import com.wakeUpTogetUp.togetUp.api.alarm.model.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Integer> {
    @Override
    <S extends Alarm> S save(S entity);

    @Override
    Optional<Alarm> findById(Integer integer);

    @Query("select a from Alarm a where a.id = :id and a.user.id = :userId")
    Optional<Alarm> findById(Integer id, Integer userId);

    @Query("select a from Alarm a where a.user.id = :userId")
    List<Alarm> findByUserId(@Param(value = "userId") Integer userId);

    @Modifying
    @Query("update Alarm a set a.room.id = :roomId where a.id = :alarmId")
    void updateRoomIdByAlarmId(@Param("alarmId") Integer alarmId, @Param("roomId") Integer roomId);

    Integer countByUserId(Integer userId);


    void deleteByUserIdAndRoomIdIsNull(Integer userId);


    @Query("select a from Alarm a where a.room.id IN :roomIds")
    List<Alarm> findAllByRoomIds(@Param("roomIds") List<Integer> roomIds);

    List<Alarm> findAllByUser_IdAndRoom_IdIsNotNull(Integer userId);

}
