package com.wakeUpTogetUp.togetUp.api.users;

import com.wakeUpTogetUp.togetUp.api.auth.AuthUser;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@Tag(name = "유저")
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/users")
public class UserController  {

    private final UserService userService;
    private final UserRepository userRepository;


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



}





