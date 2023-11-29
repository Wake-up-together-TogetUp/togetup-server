package com.wakeUpTogetUp.togetUp.api.notification;

import lombok.Getter;

@Getter
public enum DataKeyType {
    LINK("link"),
    ROOM_ID("roomId");

    private final String key;

    DataKeyType(String key) {
        this.key = key;
    }
}
