package com.wakeUpTogetUp.togetUp.mission.dto.response;

import com.wakeUpTogetUp.togetUp.alarms.model.Alarm;
import com.wakeUpTogetUp.togetUp.users.model.User;
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
