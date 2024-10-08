package com.wakeUpTogetUp.togetUp.api.alarm.controller.dto.response;

import com.wakeUpTogetUp.togetUp.api.mission.dto.response.MissionObjectRes;
import com.wakeUpTogetUp.togetUp.api.mission.dto.response.MissionRes;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "알람 조회 응답")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlarmDetailRes {
    @Schema(description = "알람 id")
    private Integer id;

    @Schema(description = "유저 id", example = "1")
    private Integer userId;

    @Schema(description = "알람 이름", example = "기상 알람")
    private String name;

    @Schema(description = "알람 시간", example = "06:00:00")
    private LocalTime alarmTime;

    @Schema(description = "월요일 울림 여부", example = "true")
    private Boolean monday;

    @Schema(description = "화요일 울림 여부", example = "true")
    private Boolean tuesday;

    @Schema(description = "수요일 울림 여부", example = "true")
    private Boolean wednesday;

    @Schema(description = "목요일 울림 여부", example = "true")
    private Boolean thursday;

    @Schema(description = "금요일 울림 여부", example = "true")
    private Boolean friday;

    @Schema(description = "토요일 울림 여부", example = "false")
    private Boolean saturday;

    @Schema(description = "일요일 울림 여부", example = "false")
    private Boolean sunday;

    @Schema(description = "진동 활성화 여부", example = "true")
    private Boolean isVibrate;

    @Schema(description = "알람 활성화 여부", example = "true")
    private Boolean isActivated;

    @Schema(description = "미션")
    private MissionRes missionRes;

    @Schema(description = "미션 객체")
    private MissionObjectRes missionObjectRes;

    @Schema(description = "그룹 정보")
    private AlarmRoomRes alarmRoomRes;
}