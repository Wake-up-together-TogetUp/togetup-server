package com.wakeUpTogetUp.togetUp.api.notification;

import com.wakeUpTogetUp.togetUp.api.notification.dto.request.BroadCastNotificationReq;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "알림(Notification)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "전체 유저에게 fcm 노티 전송")
    @PostMapping("/broadcast")
    public BaseResponse<Status> sendNotificationToAllUsers(
            @Valid @RequestBody BroadCastNotificationReq broadCastNotificationReq
    ) {
        notificationService.sendNotificationToAllUsers(broadCastNotificationReq.getTitle(), broadCastNotificationReq.getBody());

        return new BaseResponse(Status.SUCCESS);
    }


}
