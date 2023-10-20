package com.wakeUpTogetUp.togetUp.api.users;

import com.wakeUpTogetUp.togetUp.api.auth.AuthUser;
import com.wakeUpTogetUp.togetUp.api.avatar.vo.UserAvatarData;
import com.wakeUpTogetUp.togetUp.api.users.dto.request.AppleUserDeleteReq;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import javax.validation.Valid;
import java.io.IOException;

@Tag(name = "유저(User)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/users")
public class UserController  {

    private final UserService userService;
    private final UserAvatarService userAvatarService;

    @Operation(summary = "fcmToken 업데이트")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 유저 입니다.")})
    @PatchMapping("/fcm-token")
    public BaseResponse<Integer> updateFcmToken(@Parameter(hidden = true) @AuthUser Integer userId, @Parameter(description = "디바이스토큰 id")@RequestParam( required = false) Integer fcmTokenId, @Parameter(description = "토큰값",required = true) @RequestParam String fcmToken){
        Integer updatedFcmTokenId =userService.updateFcmToken(userId,fcmTokenId,fcmToken);
        return new BaseResponse<>(Status.SUCCESS,updatedFcmTokenId);
    }


    @Operation(summary="알림설정 동의 변경")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 유저 입니다.")})
    @PatchMapping("/push")
    public BaseResponse<Status> updateAgreePush(@Parameter(hidden = true) @AuthUser Integer userId,@Parameter(description = "알람동의 값",example = "true")@RequestParam() boolean agreePush){
        userService.updateAgreePush(userId,agreePush);
        return new BaseResponse<>(Status.SUCCESS);
    }


    @Operation(summary="카카오 유저 탈퇴")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 유저 입니다.")})
    @DeleteMapping()
    public BaseResponse<Status> deleteKaKaoUser(@Parameter(hidden = true) @AuthUser Integer userId){
        userService.deleteById(userId);
        return new BaseResponse<>(Status.SUCCESS);
    }

    @Operation(summary="애플 유저 탈퇴")
    @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.")
    @DeleteMapping("apple")
    public BaseResponse<Status> deleteAppleUser(@Parameter(hidden = true) @AuthUser Integer userId,
                                                @RequestBody @Valid AppleUserDeleteReq appleUserDeleteReq) throws IOException{
        userService.deleteAppleUser(userId,appleUserDeleteReq.getAuthorizationCode());
        return new BaseResponse<>(Status.SUCCESS);
    }

    @Operation(summary = "유저 아바타 목록 가져오기")
    @GetMapping("/avatars")
    public BaseResponse<List<UserAvatarData>> getUserAvatarList(
            @Parameter(hidden = true) @AuthUser Integer userId
    ) {
        return new BaseResponse<>(Status.SUCCESS, userAvatarService.findUserAvatarList(userId));
    }
}

