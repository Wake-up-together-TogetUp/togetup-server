package com.wakeUpTogetUp.togetUp.api.room;


import com.wakeUpTogetUp.togetUp.api.room.model.RoomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomUserRepository extends JpaRepository<RoomUser, Integer> {



}
