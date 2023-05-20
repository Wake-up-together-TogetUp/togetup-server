package com.wakeUpTogetUp.togetUp.alarms.dto.request;

import lombok.*;

import javax.validation.constraints.Null;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PatchAlarmReq {
    private Integer userId;
    private Integer missionId;
    private String name;
    private String icon;
    private String sound;
    private Integer volume;
    private Boolean isVibrate;
    private Boolean isRoutineOn;
    private Integer snoozeInterval;
    private Integer snoozeCnt;
    private Integer startHour;
    private Integer startMinute;
    private Boolean monday;
    private Boolean tuesday;
    private Boolean wednesday;
    private Boolean thursday;
    private Boolean friday;
    private Boolean saturday;
    private Boolean sunday;
    private Boolean isActivated;
    private List<Integer> routineIdList;
}
