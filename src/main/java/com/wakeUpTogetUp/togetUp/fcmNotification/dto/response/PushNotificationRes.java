package com.wakeUpTogetUp.togetUp.fcmNotification.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class PushNotificationRes {
    private String message;
    private String category;
    private String response;
    private String topic;

    public PushNotificationRes(String message, String category, String response) {
        this.message = message;
        this.category = category;
        this.response = response;
    }
    public PushNotificationRes(String message, String category, String response, String topic) {
        this.message = message;
        this.category = category;
        this.response = response;
        this.topic = topic;
    }
}
