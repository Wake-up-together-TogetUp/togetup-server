package com.wakeUpTogetUp.togetUp.alarm.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostAlarmReq {
    @NotNull(message = "사용자 아이디를 넣어주세요.")
    private Integer userId;
    @NotNull(message = "미션 아이디를 넣어주세요.")
    private Integer missionId;
    @NotBlank(message = "알람 이름은 공백일 수 없습니다.")
    private String name;
    private String icon;
    private String sound;
    private Boolean isVibrate;
    private Boolean isRoutineOn;
    private Integer snoozeInterval;
    private Integer snoozeCnt;
    @NotNull(message = "알람 시작시는 공백일 수 없습니다.")
    private Time alarmTime;
    private Boolean monday;
    private Boolean tuesday;
    private Boolean wednesday;
    private Boolean thursday;
    private Boolean friday;
    private Boolean saturday;
    private Boolean sunday;
}
