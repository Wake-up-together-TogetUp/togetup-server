package com.wakeUpTogetUp.togetUp.alarms.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostAlarmReq {
    @NotBlank(message = "알람 이름은 공백일 수 없습니다.")
    private String name;
    private String icon;
    private String sound;
    private int volume;
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
    private List<Integer> routineIdList;        // 알람에 등록할 루틴 리스트
}
