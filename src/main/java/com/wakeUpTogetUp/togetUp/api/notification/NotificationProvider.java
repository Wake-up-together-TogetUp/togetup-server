package com.wakeUpTogetUp.togetUp.api.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationProvider {
    private final NotificationRepository notificationRepository;

}
