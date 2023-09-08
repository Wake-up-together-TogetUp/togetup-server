package com.wakeUpTogetUp.togetUp.api.users;

import com.wakeUpTogetUp.togetUp.api.auth.AuthUser;
import com.wakeUpTogetUp.togetUp.api.auth.service.AppleLoginServiceImpl;
import com.wakeUpTogetUp.togetUp.api.auth.service.AuthService;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Tag(name = "유저")
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/users")
public class UserController  {

    private final UserService userService;
    private final UserRepository userRepository;
    private final AppleLoginServiceImpl appleLoginService;
    private final AuthService authService;


    @Operation(summary = "fcmToken 업데이트")
    @PatchMapping("/fcm-token")
    public BaseResponse<Integer> updateFcmToken(@Parameter(hidden = true) @AuthUser Integer userId, @Parameter(description = "디바이스토큰 id")@RequestParam( required = false) Integer fcmTokenId, @Parameter(description = "토큰값",required = true) @RequestParam String fcmToken){
        Integer updatedFcmTokenId =userService.updateFcmToken(userId,fcmTokenId,fcmToken);
        return new BaseResponse<>(Status.SUCCESS,updatedFcmTokenId);
    }

    @Operation(summary="알림설정 동의 변경")
    @PatchMapping("/push")
    public BaseResponse<Status> updateAgreePush(@Parameter(hidden = true) @AuthUser Integer userId,@RequestParam() boolean agreePush){
        userService.updateAgreePush(userId,agreePush);
        return new BaseResponse<>(Status.SUCCESS);
    }

    @Operation(summary="카카오 유저 탈퇴")
    @DeleteMapping()
    public BaseResponse<Status> deleteKaKaoUser(@Parameter(hidden = true) @AuthUser Integer userId){
        userService.deleteById(userId);
        return new BaseResponse<>(Status.SUCCESS);
    }

    @Operation(summary="애플 유저 탈퇴")
    @DeleteMapping("apple")
    public BaseResponse<Status> deleteAppleUser(@Parameter(hidden = true) @AuthUser Integer userId,@RequestParam() String authorizationCode ) throws IOException{
        userService.deleteAppleUser(userId,authorizationCode);
        return new BaseResponse<>(Status.SUCCESS);
    }







}





