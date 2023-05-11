package com.wakeUpTogetUp.togetUp.mappingGroupUser.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MappingGroupUserRes {
    private Integer id;
    private Integer userId;
    private Integer groupId;
    private Integer isPersonalNotice;
    private Integer isNotice;
    private Integer isHostUser;
    private Integer isActivated;
}
