package com.wakeUpTogetUp.togetUp.api.avatar;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum AvatarTheme {
    NOOB_CHICK("신입 병아리"),
    TWINKLING_TEDDY_BEAR("눈을 반짝이는 곰돌이"),
    CUTE_BUNNY("깜찍한 토끼")
    ;

    private final String value;

    private static final Map<String, AvatarTheme> valueToEnum = new HashMap<>();

    // 열거형 상수 생성 시에 value를 영어로 매핑
    static {
        for (AvatarTheme theme : AvatarTheme.values()) {
            valueToEnum.put(theme.value, theme);
        }
    }

    public String getValue() {
        return value;
    }

    // 한글 값을 받아서 해당 열거형 상수의 이름(영어)을 반환
    public static AvatarTheme fromValue(String value) {
        return valueToEnum.get(value);
    }
}
