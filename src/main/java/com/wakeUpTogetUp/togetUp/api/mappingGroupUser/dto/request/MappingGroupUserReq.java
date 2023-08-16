package com.wakeUpTogetUp.togetUp.api.mappingGroupUser.dto.request;


import com.wakeUpTogetUp.togetUp.api.group.model.Group;
import com.wakeUpTogetUp.togetUp.api.mappingGroupUser.model.MappingGroupUser;
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

