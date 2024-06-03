package com.wakeUpTogetUp.togetUp.api.notification.vo;

import java.util.List;
import java.util.Map;
import com.wakeUpTogetUp.togetUp.api.users.fcmToken.FcmToken;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class NotificationSendEvent {
    private final List<FcmToken> fcmTokens;
    private final String title;
    private final String body;
    private final Map<String, String> dataMap;

    public NotificationSendEvent(List<FcmToken> fcmTokens, String title, String body, Map<String, String> dataMap) {
        this.fcmTokens = fcmTokens;
        this.title = title;
        this.body = body;
        this.dataMap = dataMap;
    }
}
