package com.wakeUpTogetUp.togetUp.fcmNotification;

import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import com.wakeUpTogetUp.togetUp.fcmNotification.dto.request.PushNotificationReq;
import com.wakeUpTogetUp.togetUp.fcmNotification.dto.response.PushNotificationRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/push")
public class FcmController {
    private final FcmService fcmService;
    private final NotificationService notificationService;

    @GetMapping("/token")
    BaseResponse<PushNotificationRes> sendPushAlarmToToken(
            @RequestBody PushNotificationReq request
    ) throws ExecutionException, InterruptedException {
        return new BaseResponse<>(Status.SUCCESS, fcmService.sendMessageToToken(request));
    }
    @GetMapping("/tokens")
    BaseResponse<PushNotificationRes> sendPushAlarmToTokens(
            @RequestBody PushNotificationReq request
    ) throws ExecutionException, InterruptedException {
        return new BaseResponse<>(Status.SUCCESS, fcmService.sendMessageToTokens(request));
    }
    @GetMapping("/topic")
    BaseResponse<PushNotificationRes> sendPushAlarmToTopic(
            @RequestBody PushNotificationReq request
    ) throws ExecutionException, InterruptedException {
        return new BaseResponse<>(Status.SUCCESS, fcmService.sendMessageToTopic(request));
    }
    @PostMapping("/group/{groupId}")
    BaseResponse<PushNotificationRes> sendPushAlarmToGroup(
            @PathVariable Integer groupId,
            @RequestBody PushNotificationReq request
    ) throws ExecutionException, InterruptedException {
        return new BaseResponse<>(Status.SUCCESS, notificationService.sendMessageToGroup(groupId, request));
    }
}
