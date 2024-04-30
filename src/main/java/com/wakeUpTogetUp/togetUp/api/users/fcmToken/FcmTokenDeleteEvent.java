package com.wakeUpTogetUp.togetUp.api.users.fcmToken;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class FcmTokenDeleteEvent {
    private final List<String> fcmTokens;
}
