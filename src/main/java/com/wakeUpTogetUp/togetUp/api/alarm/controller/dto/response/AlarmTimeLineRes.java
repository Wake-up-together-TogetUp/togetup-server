package com.wakeUpTogetUp.togetUp.api.alarm.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "알람 타임라인 response")
public class AlarmTimeLineRes {
    @Schema(description = "현재 날짜", example = "2023-11-20")
    private LocalDate today;

    @Schema(description = "요일", example = "MONDAY")
    private DayOfWeek dayOfWeek;

    @Schema(description = "다음 알람")
    private AlarmSimpleRes nextAlarm;
    
    @Schema(description = "오늘 알람 리스트")
    private List<AlarmSimpleRes> todayAlarmList;
}