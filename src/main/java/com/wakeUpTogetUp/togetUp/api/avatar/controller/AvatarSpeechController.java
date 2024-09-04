package com.wakeUpTogetUp.togetUp.api.avatar.controller;

import com.wakeUpTogetUp.togetUp.api.auth.AuthUser;
import com.wakeUpTogetUp.togetUp.api.avatar.application.AvatarSpeechProvider;
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

@Tag(name = "아바타 (Avatar)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/avatars")
public class AvatarSpeechController {

    private final AvatarSpeechProvider avatarSpeechProvider;

    @Operation(summary = "아바타 대사 불러오기")
    @GetMapping("/{avatarId}/speeches")
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
