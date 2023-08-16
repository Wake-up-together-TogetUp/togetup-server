package com.wakeUpTogetUp.togetUp.api.mappingGroupUser;

import com.wakeUpTogetUp.togetUp.api.group.model.Group;
import com.wakeUpTogetUp.togetUp.api.mappingGroupUser.model.MappingGroupUser;
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
    private Group group;
    private Integer isHostUser;


    public MappingGroupUser toEntity(){
        return MappingGroupUser.builder()
                .id(id)
               .user(user)
                .group(group)
                .isHostUser(isHostUser)
                .build();
    }



}
