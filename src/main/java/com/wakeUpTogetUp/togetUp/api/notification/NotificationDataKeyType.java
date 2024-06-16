package com.wakeUpTogetUp.togetUp.api.notification;

import lombok.Getter;

@Getter
public enum NotificationDataKeyType {
    LINK("link"),
    ROOM_ID("roomId");

    private final String key;

    NotificationDataKeyType(String key) {
        this.key = key;
    }
}
