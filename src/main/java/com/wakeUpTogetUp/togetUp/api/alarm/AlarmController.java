package com.wakeUpTogetUp.togetUp.api.alarm;

import com.wakeUpTogetUp.togetUp.api.alarm.dto.request.PatchAlarmReq;
import com.wakeUpTogetUp.togetUp.api.alarm.dto.response.GetAlarmRes;
import com.wakeUpTogetUp.togetUp.api.alarm.dto.request.PostAlarmReq;
import com.wakeUpTogetUp.togetUp.api.auth.AuthUser;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import com.wakeUpTogetUp.togetUp.common.Status;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "알람(Alarm)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/alarm")
public class AlarmController {
    private final AlarmService alarmService;
    private final AlarmProvider alarmProvider;

    @Operation(summary = "알람 1개 불러오기")
    @ApiResponse(
            responseCode = "200",
            description = "요청에 성공하였습니다.",
            content = @Content(schema = @Schema(implementation = GetAlarmRes.class)))
    @GetMapping("/{alarmId}")
    public BaseResponse<GetAlarmRes> GetAlarm(@PathVariable Integer alarmId){
        return new BaseResponse<>(Status.SUCCESS, alarmProvider.getAlarm(alarmId));
    }

    @Operation(summary = "유저 알람 목록 불러오기")
    @ApiResponse(
            responseCode = "200",
            description = "요청에 성공하였습니다.",
            content = @Content(schema = @Schema(implementation = GetAlarmRes.class)))
    @GetMapping
    public BaseResponse<List<GetAlarmRes>> GetAlarmsByUserId(@AuthUser Integer userId){
        return new BaseResponse<>(Status.SUCCESS, alarmProvider.getAlarmsByUserId(userId));
    }

    @Operation(summary = "알람 생성")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse createAlarm(
            @Parameter(hidden = true) @AuthUser Integer userId,
            @RequestBody @Valid PostAlarmReq postAlarmReq
    ){
        alarmService.createAlarm(userId, postAlarmReq);
        return new BaseResponse(Status.SUCCESS_CREATED);
    }

    @Operation(summary = "알람 수정")
    @PatchMapping("/{alarmId}")
    public BaseResponse updateAlarm(
            @Parameter(hidden = true) @AuthUser Integer userId,
            @PathVariable Integer alarmId,
            @RequestBody @Valid PatchAlarmReq patchAlarmReq
    ) {
        alarmService.updateAlarm(userId, alarmId, patchAlarmReq);
        return new BaseResponse<>(Status.SUCCESS);
    }

    @Operation(summary = "알람 삭제")
    @DeleteMapping("/{alarmId}")
    public BaseResponse deleteAlarm(
            @Parameter(hidden = true) @AuthUser Integer userId,
            @PathVariable Integer alarmId
    ) {
        alarmService.deleteAlarm(alarmId);
        return new BaseResponse<>(Status.SUCCESS);
    }
}

