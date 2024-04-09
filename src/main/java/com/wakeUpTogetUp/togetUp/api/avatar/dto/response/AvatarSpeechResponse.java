package com.wakeUpTogetUp.togetUp.api.avatar.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AvatarSpeechResponse {

    private String speech;
}
