package com.wakeUpTogetUp.togetUp.api.mission.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MissionLogRes {
    private Integer id;
    private String alarmName;
    private String missionPicLink;
    private String createdAt;
    private Integer userId;
    private Integer roomId;
    private Integer missionId;
    private Boolean isActivated;
}
