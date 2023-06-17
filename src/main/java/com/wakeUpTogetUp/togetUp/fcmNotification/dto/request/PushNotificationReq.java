package com.wakeUpTogetUp.togetUp.fcmNotification.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PushNotificationReq {
    private String category;
    private String title;
    private String message;
    private List<String> tokenList;
    private String topic;
}
