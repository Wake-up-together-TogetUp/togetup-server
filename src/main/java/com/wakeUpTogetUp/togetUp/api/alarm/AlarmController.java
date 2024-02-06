package com.wakeUpTogetUp.togetUp.api.alarm;

import com.wakeUpTogetUp.togetUp.api.alarm.dto.request.PatchAlarmReq;
import com.wakeUpTogetUp.togetUp.api.alarm.dto.request.PostAlarmReq;
import com.wakeUpTogetUp.togetUp.api.alarm.dto.response.GetAlarmRes;
import com.wakeUpTogetUp.togetUp.api.auth.AuthUser;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "알람(Alarm)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/alarm")
public class AlarmController {
    private final AlarmService alarmService;
    private final AlarmProvider alarmProvider;

    @Operation(summary = "알람 1개 불러오기")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "요청에 성공하였습니다.",
                    content = @Content(schema = @Schema(implementation = GetAlarmRes.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 알람 입니다.")})
    @GetMapping("/{alarmId}")
    public BaseResponse<GetAlarmRes> getAlarm(@PathVariable Integer alarmId) {
        return new BaseResponse<>(Status.SUCCESS, alarmProvider.getAlarmById(alarmId));
    }

    @Operation(summary = "유저 알람 목록 불러오기", description = "개인/그룹 알람 가져오기")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "요청에 성공하였습니다.",
                    content = @Content(schema = @Schema(implementation = GetAlarmRes.class))),
            @ApiResponse(responseCode = "400", description = "요청 파라미터를 확인해주세요."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 유저 입니다.")})
    @GetMapping
    public BaseResponse<List<GetAlarmRes>> getAlarmsByUserId(
            @Parameter(description = "- 개인 : personal\n- 그룹 : group", example = "personal") String type,
            @Parameter(hidden = true) @AuthUser Integer userId
    ) {
        return new BaseResponse<>(Status.SUCCESS, alarmProvider.getAlarmsByUserIdOrderByDate(userId, type));
    }

    @Operation(summary = "알람 생성")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "생성 되었습니다.",
                    content = @Content(schema = @Schema(implementation = Integer.class))),
            @ApiResponse(responseCode = "400", description = "미션과 미션 객체 id 값이 일치하지 않습니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 유저 입니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 미션 입니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 미션 객체 입니다."),
    })
    public BaseResponse<Integer> createAlarm(
            @Parameter(hidden = true) @AuthUser Integer userId,
            @RequestBody @Valid PostAlarmReq postAlarmReq
    ) {
        return new BaseResponse<>(Status.SUCCESS_CREATED, alarmService.createAlarm(userId, postAlarmReq).getId());
    }

    @Operation(summary = "알람 수정")
    @PatchMapping("/{alarmId}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "요청에 성공하였습니다.",
                    content = @Content(schema = @Schema(implementation = Integer.class))),
            @ApiResponse(responseCode = "400", description = "미션과 미션 객체 id 값이 일치하지 않습니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 알람 입니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 미션 입니다."),
    })
    public BaseResponse<Integer> updateAlarm(
            @Parameter(hidden = true) @AuthUser Integer userId,
            @PathVariable Integer alarmId,
            @RequestBody @Valid PatchAlarmReq patchAlarmReq
    ) {
        return new BaseResponse<>(Status.SUCCESS, alarmService.updateAlarm(userId, alarmId, patchAlarmReq).getId());
    }

    @Operation(summary = "알람 삭제")
    @DeleteMapping("/{alarmId}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "요청에 성공하였습니다.",
                    content = @Content(schema = @Schema(implementation = Integer.class))),
            @ApiResponse(responseCode = "409", description = "이미 보유한 아바타 입니다.")
    })
    public BaseResponse<Integer> deleteAlarm(
            @Parameter(hidden = true) @AuthUser Integer userId,
            @PathVariable Integer alarmId
    ) {
        return new BaseResponse<>(Status.SUCCESS, alarmService.deleteAlarm(alarmId));
    }
}

