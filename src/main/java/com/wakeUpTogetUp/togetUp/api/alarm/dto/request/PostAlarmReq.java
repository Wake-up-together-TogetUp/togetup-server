package com.wakeUpTogetUp.togetUp.api.alarm.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
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
    @Schema(description = "알람 이름", requiredMode = RequiredMode.REQUIRED, example = "기상")
    @NotBlank(message = "알람 이름은 공백일 수 없습니다.")
    private String name;

    @Schema(description = "아이콘", defaultValue = "⏰", example = "⏰")
    private String icon;

    @Schema(description = "다시울림 간격", defaultValue = "5", example = "5")
    private Integer snoozeInterval;

    @Schema(description = "다시울림 횟수", defaultValue = "3", example = "3")
    private Integer snoozeCnt;

    @Schema(description = "알람 시간", requiredMode = RequiredMode.REQUIRED, example = "06:00")
    @NotNull(message = "알람 시간은 공백일 수 없습니다.")
    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$", message = "HH:mm 형식이 아닙니다.")
    private String alarmTime;

    @Schema(description = "월요일 울림 여부", defaultValue = "false", example = "false")
    private Boolean monday;

    @Schema(description = "화요일 울림 여부", defaultValue = "false", example = "false")
    private Boolean tuesday;

    @Schema(description = "수요일 울림 여부", defaultValue = "false", example = "false")
    private Boolean wednesday;

    @Schema(description = "목요일 울림 여부", defaultValue = "false", example = "false")
    private Boolean thursday;

    @Schema(description = "금요일 울림 여부", defaultValue = "false", example = "false")
    private Boolean friday;

    @Schema(description = "토요일 울림 여부", defaultValue = "false", example = "false")
    private Boolean saturday;

    @Schema(description = "일요일 울림 여부", defaultValue = "false", example = "false")
    private Boolean sunday;

    @Schema(description = "다시울림 활성화 여부", defaultValue = "true", example = "true")
    private Boolean isSnoozeActivated;

    @Schema(description = "진동 활성화 여부", defaultValue = "true", example = "true")
    private Boolean isVibrate;
    
    @Schema(description = "미션 id", requiredMode = RequiredMode.REQUIRED, example = "2")
    private Integer missionId;

    @Schema(description = "미션 객체 id", requiredMode = RequiredMode.REQUIRED, example = "1")
    private Integer missionObjectId;

    @Schema(description = "그룹 id", requiredMode = RequiredMode.REQUIRED, example = "1")
    private Integer roomId;
}
