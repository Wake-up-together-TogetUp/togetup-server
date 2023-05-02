package com.wakeUpTogetUp.togetUp.alarms.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAlarmsRes {
    private Integer id;
    private Integer userId;
    private String name;
    private String icon;
    private String sound;
    private Integer volume;
    private Boolean isVibrate;
    private Boolean isRoutineOn;
    private Integer snoozeInterval;
    private Integer snoozeCnt;
    private String startHour;
    private String startMinute;
    private Boolean monday;
    private Boolean tuesday;
    private Boolean wednesday;
    private Boolean thursday;
    private Boolean friday;
    private Boolean saturday;
    private Boolean sunday;
    private Boolean isActivated;
}
