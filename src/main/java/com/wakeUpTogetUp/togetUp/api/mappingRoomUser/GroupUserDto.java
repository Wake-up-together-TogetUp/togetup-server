package com.wakeUpTogetUp.togetUp.api.mappingRoomUser;

import com.wakeUpTogetUp.togetUp.api.room.model.Room;
import com.wakeUpTogetUp.togetUp.api.room.model.RoomUser;
import com.wakeUpTogetUp.togetUp.api.users.model.User;
import lombok.*;




@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GroupUserDto {

    private Integer id;
    private User user;
    private Room room;
    private Integer isHostUser;


    public RoomUser toEntity(){
        return RoomUser.builder()
                .id(id)
               .user(user)
                .room(room)
//                .isHostUser(isHostUser)
                .build();
    }



}
