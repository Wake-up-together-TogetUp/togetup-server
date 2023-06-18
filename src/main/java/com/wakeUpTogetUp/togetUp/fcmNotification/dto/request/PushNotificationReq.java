package com.wakeUpTogetUp.togetUp.fcmNotification.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PushNotificationReq {
    private String category;
    private String title;
    private String message;
    private List<String> tokenList;
    private String topic;

    public PushNotificationReq(String category, String title, String message) {
        this.category = category;
        this.title = title;
        this.message = message;
    }
}
