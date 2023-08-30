package com.wakeUpTogetUp.togetUp.api.fcmNotification.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotificationRes {
    private String title;
    private String content;
    private String sendAt;
    private String createdAt;
}