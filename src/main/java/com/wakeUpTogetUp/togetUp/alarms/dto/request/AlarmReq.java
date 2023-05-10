package com.wakeUpTogetUp.togetUp.alarms.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AlarmReq {
    @Builder
    public AlarmReq(String name, String icon, String sound, Integer volume, Boolean isVibrate, Boolean isRoutineOn, Integer snoozeInterval, Integer snoozeCnt, Integer startHour, Integer startMinute, Boolean monday, Boolean tuesday, Boolean wednesday, Boolean thursday, Boolean friday, Boolean saturday, Boolean sunday, List<Integer> routineIdList) {
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
        this.routineIdList = routineIdList;
    }

    @Null
    private Integer id;
    @NotNull(message = "사용자 아이디를 넣어주세요.")
    private Integer userId;
    @NotBlank(message = "알람 이름은 공백일 수 없습니다.")
    private String name;
    private String icon;
    private String sound;
    private Integer volume;
    private Boolean isVibrate;
    private Boolean isRoutineOn;
    private Integer snoozeInterval;
    private Integer snoozeCnt;
    @NotNull(message = "알람 시작시는 공백일 수 없습니다.")
    private Integer startHour;
    @NotNull(message = "알람 시작분은 공백일 수 없습니다.")
    private Integer startMinute;
    private Boolean monday;
    private Boolean tuesday;
    private Boolean wednesday;
    private Boolean thursday;
    private Boolean friday;
    private Boolean saturday;
    private Boolean sunday;
    private List<Integer> routineIdList;        // 알람에 등록할 루틴 리스트
}
