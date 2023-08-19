package com.wakeUpTogetUp.togetUp.api.alarm.dto.response;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAlarmRes {
    private Integer id;
    private Integer userId;
    private Integer missionId;
    private String name;
    private String icon;
    private Boolean isVibrate;
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
}


