package com.wakeUpTogetUp.togetUp.api.mission.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostMissionCompleteLogReq {
    private Integer userId;
    private Integer alarmId;
    private String type;
    private String title;
    private String picLink;
}
