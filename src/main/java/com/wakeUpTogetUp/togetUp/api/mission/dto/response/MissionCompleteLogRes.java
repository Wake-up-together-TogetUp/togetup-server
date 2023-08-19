package com.wakeUpTogetUp.togetUp.api.mission.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MissionCompleteLogRes {
    private Integer id;
    private String type;
    private String title;
    private String picLink;
    private String createdAt;
    private Integer userId;
    private Integer alarmId;
    private Boolean isActivated;
}
