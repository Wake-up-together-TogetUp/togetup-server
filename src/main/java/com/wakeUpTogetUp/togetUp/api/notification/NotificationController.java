package com.wakeUpTogetUp.togetUp.api.notification;

import com.wakeUpTogetUp.togetUp.api.notification.dto.request.PushNotificationReq;
import com.wakeUpTogetUp.togetUp.api.notification.dto.response.NotificationRes;
import com.wakeUpTogetUp.togetUp.api.notification.dto.response.PushNotificationRes;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Tag(name = "알림(Notification)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/notification")
public class NotificationController {
    private final FcmService fcmService;
    private final NotificationService notificationService;
    private final NotificationProvider notificationProvider;

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
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/group/{groupId}")
    BaseResponse<PushNotificationRes> sendPushAlarmToGroup(
            @PathVariable Integer groupId,
            @RequestBody PushNotificationReq request
    ) throws ExecutionException, InterruptedException {
        return new BaseResponse<>(Status.SUCCESS_CREATED, notificationService.sendMessageToGroup(groupId, request));
    }

    // 유저 알림 목록 가져오기
    @GetMapping("/user/{userId}")
    BaseResponse<List<NotificationRes>> getUserPushLogList(
            @PathVariable Integer userId
    ){
        return new BaseResponse<>(Status.SUCCESS, notificationProvider.getUserPushLogList(userId));
    }
}
