package com.wakeUpTogetUp.togetUp.api.avatar.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum AvatarTheme {
    NOOB_CHICK("신입 병아리"),
    TWINKLING_TEDDY_BEAR("눈을 반짝이는 곰돌이"),
    CUTE_BUNNY("깜찍한 토끼"),
    GLUTTON_PANDA("먹보 판다"),
    RAINY_DAY_PUPPY("비오는 날 강아지"),
    PHILOSOPHER_RACCOON("철학자 너구리")
    ;

    private final String value;
}
