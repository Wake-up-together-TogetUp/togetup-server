package com.wakeUpTogetUp.togetUp.api.room;


import com.wakeUpTogetUp.togetUp.api.room.model.RoomUser;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomUserRepository extends JpaRepository<RoomUser, Integer> {

    List<RoomUser> findByUserId(Integer userId);

    @Query("SELECT ru FROM RoomUser ru " +
        "JOIN User u ON (ru.user.id = u.id) " +
        "WHERE ru.room.id = :roomId " +
        "ORDER BY " +
        "CASE WHEN ru.user.id = :userId THEN 0 " +
        "WHEN ru.isHost = true THEN 1 " +
        "ELSE 2 " +
        "END, u.name")
    List<RoomUser> findAllByRoom_IdOrderByPreference(Integer roomId ,Integer userId);


    @Query("SELECT ru.room.id " +
            "FROM RoomUser ru " +
            "WHERE ru.user.id = :userId " +
            "ORDER BY ru.createdAt DESC")
    List<Integer> findAllRoomIdsByUserId(@Param("userId") Integer userId);

    Integer countByUserId(Integer userId);
    void deleteByUserId(Integer userId);

    RoomUser findByRoom_IdAndUser_Id(Integer roomId , Integer userId);

    void deleteByRoom_IdAndUser_Id(Integer roomId, Integer userId);
    List<RoomUser> findAllByRoom_IdOrderByCreatedAt(Integer roomId);



}
