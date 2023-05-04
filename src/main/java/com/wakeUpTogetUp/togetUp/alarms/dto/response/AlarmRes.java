package com.wakeUpTogetUp.togetUp.alarms.dto.response;

import com.wakeUpTogetUp.togetUp.routines.dto.response.RoutineRes;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlarmRes {
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
    private List<RoutineRes> routineResList;
}
