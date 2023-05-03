package com.wakeUpTogetUp.togetUp.mappingGroupUser;

import com.wakeUpTogetUp.togetUp.group.model.Group;
import com.wakeUpTogetUp.togetUp.mappingGroupUser.model.MappingGroupUser;
import com.wakeUpTogetUp.togetUp.users.model.User;
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
