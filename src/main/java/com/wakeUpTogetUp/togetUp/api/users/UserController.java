package com.wakeUpTogetUp.togetUp.api.users;

import com.wakeUpTogetUp.togetUp.api.auth.AuthUser;
import com.wakeUpTogetUp.togetUp.api.auth.service.AuthService;
import com.wakeUpTogetUp.togetUp.api.avatar.application.UserAvatarQueryService;
import com.wakeUpTogetUp.togetUp.api.avatar.application.UserAvatarService;
import com.wakeUpTogetUp.togetUp.api.avatar.dto.response.UserAvatarResponse;
import com.wakeUpTogetUp.togetUp.api.users.dto.request.AppleUserDeleteReq;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "유저(User)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/users")
public class UserController {

    private final UserService userService;
    private final UserAvatarService userAvatarService;
    private final UserAvatarQueryService userAvatarQueryService;
    private final AuthService authService;

    @Operation(summary = "fcmToken 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 유저 입니다.")})
    @PostMapping("/fcm-token")
    public BaseResponse<Integer> updateFcmToken(
            @Parameter(hidden = true) @AuthUser Integer userId,
            @Parameter(description = "토큰값", required = true) @RequestParam String fcmToken) {
        userService.registerFcmToken(userId, fcmToken);
        return new BaseResponse<>(Status.SUCCESS_CREATED);
    }


    @Operation(summary = "알림설정 동의 변경")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 유저 입니다.")})
    @PatchMapping("/push")
    public BaseResponse<Status> updateAgreePush(
            @Parameter(hidden = true) @AuthUser Integer userId,
            @Parameter(description = "알람동의 값", example = "true") @RequestParam boolean agreePush) {
        userService.updateAgreePush(userId, agreePush);
        return new BaseResponse<>(Status.SUCCESS);
    }


    @Operation(summary = "카카오 유저 탈퇴")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 유저 입니다.")})
    @DeleteMapping()
    public BaseResponse<Status> deleteKaKaoUser(
            @Parameter(hidden = true) @AuthUser Integer userId) {
        userService.deleteById(userId);
        return new BaseResponse<>(Status.SUCCESS);
    }

    @Operation(summary = "애플 유저 탈퇴")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 유저 입니다."),
    })
    @DeleteMapping("apple")
    public BaseResponse<Status> deleteAppleUser(
            @Parameter(hidden = true) @AuthUser Integer userId,
            @RequestBody @Valid AppleUserDeleteReq appleUserDeleteReq) throws IOException {
        authService.disconnectApple(appleUserDeleteReq.getAuthorizationCode());
        userService.deleteById(userId);
        return new BaseResponse<>(Status.SUCCESS);
    }

    @Operation(summary = "아바타 목록 가져오기")
    @GetMapping("/avatars")
    public BaseResponse<List<UserAvatarResponse>> getUserAvatarList(
            @Parameter(hidden = true) @AuthUser Integer userId
    ) {
        return new BaseResponse<>(Status.SUCCESS, userAvatarQueryService.findUserAvatarList(userId));
    }

    @Operation(summary = "아바타 변경")
    @PatchMapping("/avatars/{avatarId}/change")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다."),
            @ApiResponse(responseCode = "403", description = "유저가 보유하지 않은 아바타 ID 입니다.")})
    public BaseResponse<Object> updateUserAvatar(
            @Parameter(hidden = true) @AuthUser Integer userId,
            @PathVariable int avatarId
    ) {
        userAvatarService.changeUserAvatar(userId, avatarId);
        return new BaseResponse<>(Status.SUCCESS);
    }
}