package com.wakeUpTogetUp.togetUp.mappingGroupUser.dto.request;


import com.wakeUpTogetUp.togetUp.group.model.Group;
import com.wakeUpTogetUp.togetUp.mappingGroupUser.model.MappingGroupUser;
import com.wakeUpTogetUp.togetUp.users.model.User;
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


    public MappingGroupUser toEntity(User user, Group group){
        return MappingGroupUser.builder()
                .id(id)
                .user(user)
                .group(group)
                .isPersonalNotice(isPersonalNotice)
                .isNotice(isNotice)
                .isHostUser(isHostUser)
                .build();
    }


}

