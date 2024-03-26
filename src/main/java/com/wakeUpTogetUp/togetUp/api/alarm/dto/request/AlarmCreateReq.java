package com.wakeUpTogetUp.togetUp.api.alarm.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "알람 생성 request")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(staticName = "of")
public class AlarmCreateReq {
    @Schema(description = "알람 이름", requiredMode = RequiredMode.REQUIRED, example = "기상")
    @NotBlank(message = "알람 이름은 공백일 수 없습니다.")
    private String name;

    @Schema(description = "알람 시간", requiredMode = RequiredMode.REQUIRED, example = "06:00:00")
    @NotNull(message = "알람 시간은 공백일 수 없습니다.")
    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$", message = "HH:mm 형식이 아닙니다.")
    private String alarmTime;

    @Schema(description = "월요일 울림 여부", defaultValue = "false", example = "false")
    private boolean monday;

    @Schema(description = "화요일 울림 여부", defaultValue = "false", example = "false")
    private boolean tuesday;

    @Schema(description = "수요일 울림 여부", defaultValue = "false", example = "false")
    private boolean wednesday;

    @Schema(description = "목요일 울림 여부", defaultValue = "false", example = "false")
    private boolean thursday;

    @Schema(description = "금요일 울림 여부", defaultValue = "false", example = "false")
    private boolean friday;

    @Schema(description = "토요일 울림 여부", defaultValue = "false", example = "false")
    private boolean saturday;

    @Schema(description = "일요일 울림 여부", defaultValue = "false", example = "false")
    private boolean sunday;

    @Schema(description = "진동 활성화 여부", defaultValue = "true", example = "true",name = "isVibrate")
    private Boolean isVibrate;

    @Schema(description = "미션 id", requiredMode = RequiredMode.REQUIRED, example = "2")
    private Integer missionId;

    @Schema(description = "미션 객체 id", requiredMode = RequiredMode.REQUIRED, example = "1")
    private Integer missionObjectId;
}
