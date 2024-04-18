package com.wakeUpTogetUp.togetUp.api.notification;

import com.wakeUpTogetUp.togetUp.api.notification.vo.NotificationSendVo;
import com.wakeUpTogetUp.togetUp.infra.fcm.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class NotificationEventHandler {
    private final FcmService fcmService;

    @Async()
    @EventListener(NotificationSendVo.class)
    public void handleSendPushEvent(NotificationSendVo event) {
        fcmService.sendMessageToTokens(event);
    }
}
