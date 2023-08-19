package com.wakeUpTogetUp.togetUp.api.mappingRoomUser.dto.request;


import com.wakeUpTogetUp.togetUp.api.group.model.Room;
import com.wakeUpTogetUp.togetUp.api.group.model.RoomUser;
import com.wakeUpTogetUp.togetUp.api.users.model.User;
import lombok.*;




@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class MappingGroupUserReq {

    private Integer id;
    private Integer isPersonalNotice; //개인미션 알림 여부
    private Integer isNotice; //채팅방 알림여부
    private Integer isHostUser;


    public RoomUser toEntity(User user, Room room){
        return RoomUser.builder()
                .id(id)
                .user(user)
                .room(room)
                .isPersonalNotice(isPersonalNotice)
                .isNotice(isNotice)
                .isHostUser(isHostUser)
                .build();
    }


}

