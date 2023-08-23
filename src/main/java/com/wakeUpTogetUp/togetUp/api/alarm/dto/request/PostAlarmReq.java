package com.wakeUpTogetUp.togetUp.api.alarm.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Pattern;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Schema(description = "알람 생성 request")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostAlarmReq {
    @Schema(description = "미션 id", example = "1")
    @NotNull(message = "미션 아이디를 넣어주세요.")
    private Integer missionId;

    private Integer roomId;

    @NotBlank(message = "알람 이름은 공백일 수 없습니다.")
    private String name;

    @Schema(description = "아이콘", example = "⏰")
    private String icon;

    @Schema(description = "진동 여부", example = "true")
    private Boolean isVibrate;

    @Schema(description = "다시울림 간격", example = "5")
    private Integer snoozeInterval;

    @Schema(description = "다시울림 횟수", example = "3")
    private Integer snoozeCnt;

    @Schema(description = "알람 시간", example = "06:00")
    @NotNull(message = "알람 시간은 공백일 수 없습니다.")
    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$", message = "HH:mm 형식이 아닙니다.")
    private String alarmTime;

    @Schema(description = "월요일 울림 여부", example = "false")
    private Boolean monday;

    @Schema(description = "화요일 울림 여부", example = "false")
    private Boolean tuesday;

    @Schema(description = "수요일 울림 여부", example = "false")
    private Boolean wednesday;

    @Schema(description = "목요일 울림 여부", example = "false")
    private Boolean thursday;

    @Schema(description = "금요일 울림 여부", example = "false")
    private Boolean friday;

    @Schema(description = "토요일 울림 여부", example = "false")
    private Boolean saturday;

    @Schema(description = "일요일 울림 여부", example = "false")
    private Boolean sunday;

    @Schema(description = "알람 활성화 여부", example = "true")
    private Boolean isActivated;
}
