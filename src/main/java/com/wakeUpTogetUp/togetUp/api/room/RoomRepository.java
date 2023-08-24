package com.wakeUpTogetUp.togetUp.api.room;


import com.wakeUpTogetUp.togetUp.api.room.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {



}
