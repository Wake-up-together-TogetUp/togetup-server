package com.wakeUpTogetUp.togetUp.alarms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostAlarmReq {
    private Long userId;
    private String name;
    private String icon;
    private String sound;
    private int volume;
    private Boolean isEnabled;
    private Boolean isVibrate;
    private Boolean isRoutineOn;
    private int snoozeInterval;
    private int snoozeCnt;
    private String startHour;
    private String startMinute;
    private Boolean monday;
    private Boolean tuesday;
    private Boolean wednesday;
    private Boolean thursday;
    private Boolean friday;
    private Boolean saturday;
    private Boolean sunday;
}
