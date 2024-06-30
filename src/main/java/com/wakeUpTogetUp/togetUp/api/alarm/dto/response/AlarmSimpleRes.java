package com.wakeUpTogetUp.togetUp.api.alarm.dto.response;

import com.wakeUpTogetUp.togetUp.api.alarm.domain.AlarmType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "알람 간단 정보 response")
public class AlarmSimpleRes {
    @Schema(description = "알람 id")
    private Integer id;

    @Schema(description = "아이콘", example = "⏰")
    private String icon;

    @Schema(name = "alarm_time", example = "06:00:00")
    private LocalTime alarmTime;

    @Schema(description = "알람 이름", example = "기상 알람")
    private String name;

    @Schema(description = "미션 객체", example = "시계")
    private String missionObject;

    @Schema(description = "알람 종류 (`개인`, `그룹`)")
    private AlarmType alarmType;
}
