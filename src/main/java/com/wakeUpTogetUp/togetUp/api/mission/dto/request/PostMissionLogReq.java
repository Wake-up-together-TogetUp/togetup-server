package com.wakeUpTogetUp.togetUp.api.mission.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostMissionLogReq {
    private String alarmName;
    private String missionPicLink;
    private Integer userId;
    private Integer roomId;
    private Integer missionId;
}
