package com.wakeUpTogetUp.togetUp.api.avatar.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AvatarSpeechResponse {

    private String speech;
}
