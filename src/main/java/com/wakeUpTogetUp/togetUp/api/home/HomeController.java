package com.wakeUpTogetUp.togetUp.api.home;

import com.wakeUpTogetUp.togetUp.api.alarm.AlarmProvider;
import com.wakeUpTogetUp.togetUp.api.alarm.dto.response.AlarmTimeLineRes;
import com.wakeUpTogetUp.togetUp.api.auth.AuthUser;
import com.wakeUpTogetUp.togetUp.api.avatar.AvatarSpeechProvider;
import com.wakeUpTogetUp.togetUp.api.avatar.dto.response.AvatarSpeechResponse;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "홈(home)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/home")
public class HomeController {

    private final AlarmProvider alarmProvider;
    private final AvatarSpeechProvider avatarSpeechProvider;

    @Operation(summary = "브리핑 보드 - 알람 타임라인")
    @GetMapping("/brief-board/alarm/timeline")
    @ApiResponse(
            responseCode = "200",
            description = "요청에 성공하였습니다.",
            content = @Content(schema = @Schema(implementation = AlarmTimeLineRes.class)))
    BaseResponse<AlarmTimeLineRes> getAlarmTimeLineOfUser(
            @Parameter(hidden = true) @AuthUser Integer userId
    ) {
        return new BaseResponse<>(Status.SUCCESS, alarmProvider.getAlarmTimeLineByUserId(userId));
    }

    @Operation(summary = "아바타 대사 불러오기")
    @GetMapping("/avatars/{avatarId}/speeches")
    @ApiResponse(
            responseCode = "200",
            description = "요청에 성공하였습니다.",
            content = @Content(schema = @Schema(implementation = AvatarSpeechResponse.class)))
    BaseResponse<AvatarSpeechResponse> getAvatarSpeech(
            @Parameter(hidden = true) @AuthUser Integer userId,
            @Parameter @PathVariable Integer avatarId
    ) {
        return new BaseResponse<>(
                Status.SUCCESS,
                avatarSpeechProvider.getUserAvatarSpeech(userId, avatarId));
    }
}
