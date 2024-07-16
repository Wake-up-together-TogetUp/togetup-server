package com.wakeUpTogetUp.togetUp.api.room;


import com.wakeUpTogetUp.togetUp.api.room.model.RoomUser;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomUserRepository extends JpaRepository<RoomUser, Integer> {



    Integer countByUserId(Integer userId);


    void deleteByUserId(Integer userId);

    Optional<RoomUser> findByRoom_IdAndUser_Id(Integer roomId, Integer userId);







}
