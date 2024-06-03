package com.wakeUpTogetUp.togetUp.api.room;


import com.wakeUpTogetUp.togetUp.api.room.dto.response.RoomRes;
import com.wakeUpTogetUp.togetUp.api.room.model.Room;
import com.wakeUpTogetUp.togetUp.api.room.model.RoomUser;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoomQueryRepository {

    Optional<Room> findById(Integer roomId);

    List<RoomRes> findRoomsOrderedByJoinTime(@Param("userId") Integer userId);

    List<RoomUser> findAllByRoomIdOrderByUserIdAndUserName(Integer userId, Integer roomId);

    Optional<Room> findByInvitationCode(String invitationCode);
}
