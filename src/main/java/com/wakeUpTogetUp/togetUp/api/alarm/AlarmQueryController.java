package com.wakeUpTogetUp.togetUp.api.alarm;

import com.wakeUpTogetUp.togetUp.api.alarm.dto.response.AlarmDetailRes;
import com.wakeUpTogetUp.togetUp.api.alarm.dto.response.AlarmTimeLineRes;
import com.wakeUpTogetUp.togetUp.api.alarm.service.AlarmQueryService;
import com.wakeUpTogetUp.togetUp.api.auth.AuthUser;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.common.annotation.LogExecutionTime;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "알람 (Alarm)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/alarms")
public class AlarmQueryController {

    private final AlarmQueryService alarmQueryService;

    @Operation(summary = "알람 상세 조회")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "요청에 성공하였습니다.",
                    content = @Content(schema = @Schema(implementation = AlarmDetailRes.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 알람 입니다.")
    })
    @GetMapping("/{alarmId}")
    public BaseResponse<AlarmDetailRes> getAlarm(@PathVariable Integer alarmId) {
        return new BaseResponse<>(Status.SUCCESS, alarmQueryService.getAlarmById(alarmId));
    }

    @Operation(summary = "특정 유저의 알람 목록 조회", description = "개인/그룹 알람 가져오기")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "요청에 성공하였습니다.",
                    content = @Content(schema = @Schema(implementation = AlarmDetailRes.class))),
            @ApiResponse(responseCode = "400", description = "요청 파라미터를 확인해주세요."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 유저 입니다.")
    })
    @GetMapping
    public BaseResponse<List<AlarmDetailRes>> getAlarmsByUserId(
            @Parameter(description = "- 개인 : personal\n- 그룹 : group", example = "personal") String type,
            @Parameter(hidden = true) @AuthUser Integer userId
    ) {
        return new BaseResponse<>(Status.SUCCESS,
                alarmQueryService.getAlarmsByUserIdOrderByDate(userId, type));
    }

    @LogExecutionTime
    @Operation(summary = "알람 타임라인 조회")
    @GetMapping("/timeline")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "요청에 성공하였습니다.",
                    content = @Content(schema = @Schema(implementation = AlarmTimeLineRes.class)))
    })
    BaseResponse<AlarmTimeLineRes> getAlarmTimeLineOfUser(
            @Parameter(hidden = true) @AuthUser Integer userId
    ) {
        return new BaseResponse<>(
                Status.SUCCESS, alarmQueryService.getTimeLine(userId, LocalDateTime.now()));
    }
}
