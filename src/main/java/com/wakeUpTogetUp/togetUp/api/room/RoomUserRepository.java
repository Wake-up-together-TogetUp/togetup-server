package com.wakeUpTogetUp.togetUp.api.room;


import com.wakeUpTogetUp.togetUp.api.room.model.RoomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomUserRepository extends JpaRepository<RoomUser, Integer> {

    List<RoomUser> findByUserId(Integer userId);
    List<RoomUser> findAllByRoom_Id(Integer roomId);


    Integer countByUserId(Integer userId);
    void deleteByUserId(Integer userId);




}
