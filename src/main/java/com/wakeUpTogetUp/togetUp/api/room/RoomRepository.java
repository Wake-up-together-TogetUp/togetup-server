package com.wakeUpTogetUp.togetUp.api.room;


import com.wakeUpTogetUp.togetUp.api.room.dto.response.RoomDetailRes;
import com.wakeUpTogetUp.togetUp.api.room.model.Room;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    @Query("SELECT r FROM Room r JOIN Alarm a ON r.id = a.room.id WHERE a.id= :alarmId")
    Optional<Room> findByAlarmId(@Param("alarmId") Integer alarmId);
    Optional<Room> findByInvitationCode(@Param("invitationCode") String invitationCode);


}
