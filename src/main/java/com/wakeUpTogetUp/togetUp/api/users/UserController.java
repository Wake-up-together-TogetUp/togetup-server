package com.wakeUpTogetUp.togetUp.api.users;

import com.wakeUpTogetUp.togetUp.api.auth.AuthUser;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/app/users")
public class UserController  {

    private final UserService userService;
    private final UserRepository userRepository;

    @PatchMapping("/fcm-token/{fcmTokenId}")
    public BaseResponse<Status> updateFcmToken(@AuthUser Integer userId,@RequestBody String fcmToken){

        return new BaseResponse<>(Status.SUCCESS);
    }

    @GetMapping("/notification")
    public BaseResponse<Status> updateAgreeNotification(@AuthUser Integer userId,@RequestBody boolean agreeNotification){

        return new BaseResponse<>(Status.SUCCESS);
    }



}





