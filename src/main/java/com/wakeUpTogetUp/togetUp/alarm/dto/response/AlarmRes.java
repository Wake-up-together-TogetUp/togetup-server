package com.wakeUpTogetUp.togetUp.alarm.dto.response;

import com.wakeUpTogetUp.togetUp.routine.dto.response.RoutineRes;
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
    private Integer missionId;
    private String name;
    private String icon;
    private String sound;
    private Boolean isVibrate;
    private Boolean isRoutineOn;
    private Integer snoozeInterval;
    private Integer snoozeCnt;
    private String alarmTime;
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
