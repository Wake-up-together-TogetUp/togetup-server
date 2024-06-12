package com.wakeUpTogetUp.togetUp.api.notification.vo;


import com.wakeUpTogetUp.togetUp.api.notification.DataKeyType;
import com.wakeUpTogetUp.togetUp.api.notification.DataValueType;
import com.wakeUpTogetUp.togetUp.api.users.fcmToken.FcmToken;

import java.util.List;
import java.util.Map;

public class AvatarUnlockedNotificationEvent extends NotificationSendEvent {
    private static final String title = "새로운 아바타가 기다리고 있어요!";
    private static final String body = "확인해보세요!";

    private static final Map<String, String> dataMap = Map.of(DataKeyType.LINK.getKey(), DataValueType.AVATAR.toString());

    public AvatarUnlockedNotificationEvent(List<FcmToken> fcmTokens) {
        super(fcmTokens, title, body, dataMap);
    }
}
