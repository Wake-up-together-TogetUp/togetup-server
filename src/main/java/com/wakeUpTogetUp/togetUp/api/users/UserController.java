package com.wakeUpTogetUp.togetUp.api.users;

import com.wakeUpTogetUp.togetUp.api.auth.AuthUser;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/app/users")
public class UserController  {

    private final UserService userService;
    private final UserRepository userRepository;

    @PatchMapping("/fcm-token")
    public BaseResponse<Integer> updateFcmToken(@AuthUser Integer userId, @RequestParam( required = false) Integer fcmTokenId, @RequestParam String fcmToken){
        Integer updatedFcmTokenId =userService.updateFcmToken(userId,fcmTokenId,fcmToken);
        return new BaseResponse<>(Status.SUCCESS,updatedFcmTokenId);
    }

    @PatchMapping("/push")
    public BaseResponse<Status> updateAgreePush(@AuthUser Integer userId,@RequestParam boolean agreePush){
        userService.updateAgreePush(userId,agreePush);
        return new BaseResponse<>(Status.SUCCESS);
    }



}





