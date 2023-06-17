package com.wakeUpTogetUp.togetUp.fcmNotification;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import com.wakeUpTogetUp.togetUp.fcmNotification.dto.request.PushNotificationReq;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/push")
public class FcmController {
    private final FirebaseService firebaseService;
    @GetMapping("/token")
    BaseResponse<String> sendPushAlarmToToken(
            @RequestBody PushNotificationReq request
    ) throws ExecutionException, InterruptedException {
        return new BaseResponse<>(Status.SUCCESS, firebaseService.sendMessageToToken(request));
    }
    @GetMapping("/tokens")
    BaseResponse<String> sendPushAlarmToTokens(
            @RequestBody PushNotificationReq request
    ) throws ExecutionException, InterruptedException {
        return new BaseResponse<>(Status.SUCCESS, firebaseService.sendMessageToTokens(request));
    }
    @GetMapping("/topic")
    BaseResponse<String> sendPushAlarmToTopic(
            @RequestBody PushNotificationReq request
    ) throws ExecutionException, InterruptedException {
        return new BaseResponse<>(Status.SUCCESS, firebaseService.sendMessageToTopic(request));
    }
}
