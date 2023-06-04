package com.wakeUpTogetUp.togetUp.alarms.dto.request;

import com.wakeUpTogetUp.togetUp.routines.dto.request.PostRoutineReq;
import lombok.*;

import java.sql.Time;
import java.util.List;

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
    private Boolean isVibrate;
    private Boolean isRoutineOn;
    private Integer snoozeInterval;
    private Integer snoozeCnt;
    private Time alarmTime;
    private Boolean monday;
    private Boolean tuesday;
    private Boolean wednesday;
    private Boolean thursday;
    private Boolean friday;
    private Boolean saturday;
    private Boolean sunday;
    private List<PostRoutineReq> routineList;
}
