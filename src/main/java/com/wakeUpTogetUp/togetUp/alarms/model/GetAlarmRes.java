package com.wakeUpTogetUp.togetUp.alarms.model;

import com.wakeUpTogetUp.togetUp.routines.model.GetRoutineRes;
import com.wakeUpTogetUp.togetUp.routines.model.Routine;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAlarmRes {
    public GetAlarmRes(Integer id, Integer userId, String name, String icon, String sound, int volume, Boolean isVibrate, Boolean isRoutineOn, Integer snoozeInterval, Integer snoozeCnt, String startHour, String startMinute, Boolean monday, Boolean tuesday, Boolean wednesday, Boolean thursday, Boolean friday, Boolean saturday, Boolean sunday, Boolean isActivated) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.icon = icon;
        this.sound = sound;
        this.volume = volume;
        this.isVibrate = isVibrate;
        this.isRoutineOn = isRoutineOn;
        this.snoozeInterval = snoozeInterval;
        this.snoozeCnt = snoozeCnt;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
        this.isActivated = isActivated;
    }

    private Integer id;
    private Integer userId;
    private String name;
    private String icon;
    private String sound;
    private int volume;
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
    private List<GetRoutineRes> getRoutineResList;
}
