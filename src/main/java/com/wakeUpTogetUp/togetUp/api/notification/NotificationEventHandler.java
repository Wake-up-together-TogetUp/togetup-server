package com.wakeUpTogetUp.togetUp.api.notification;

import com.wakeUpTogetUp.togetUp.api.notification.entity.Notification;
import com.wakeUpTogetUp.togetUp.api.notification.vo.NotificationSendEvent;
import com.wakeUpTogetUp.togetUp.infra.fcm.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class NotificationEventHandler {
    private final FcmService fcmService;
    private final NotificationRepository notificationRepository;

    @Async()
    @EventListener(NotificationSendEvent.class)
    public void handleSendPushEvent(NotificationSendEvent event) {
        fcmService.sendMessageToTokens(event);
        notificationRepository.saveAll(Notification.from(event));
    }
}
