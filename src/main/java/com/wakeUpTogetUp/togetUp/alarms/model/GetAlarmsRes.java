package com.wakeUpTogetUp.togetUp.alarms.model;

import com.wakeUpTogetUp.togetUp.routines.model.Routine;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class GetAlarmsRes {
    private Long id;
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
    private String endHour;
    private String endMinute;
    private Boolean monday;
    private Boolean tuesday;
    private Boolean wednesday;
    private Boolean thursday;
    private Boolean friday;
    private Boolean saturday;
    private Boolean sunday;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp isActivated;
    private List<Routine> routineList;
}
