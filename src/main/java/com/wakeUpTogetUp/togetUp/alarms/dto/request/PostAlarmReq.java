package com.wakeUpTogetUp.togetUp.alarms.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;
import java.util.Optional;

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
    private Boolean isActivated;
    private List<Integer> routineIdList;        // 알람에 등록할 루틴 리스트
}
