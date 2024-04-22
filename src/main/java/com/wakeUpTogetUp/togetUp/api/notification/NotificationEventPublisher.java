package com.wakeUpTogetUp.togetUp.api.notification;

import com.wakeUpTogetUp.togetUp.api.notification.vo.NotificationSendEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public void publishNotificationSendEvent(NotificationSendEvent notificationSendEvent) {
        eventPublisher.publishEvent(notificationSendEvent);
    }
}

