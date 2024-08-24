package com.wakeUpTogetUp.togetUp.api.avatar.domain;

import java.util.Arrays;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum AvatarTheme {

    ASTRONAUT_BEAR
    ;

    public static boolean isExist(String name) {
        return Arrays.stream(values())
                .anyMatch(v -> v.name().equals(name));
    }
}
