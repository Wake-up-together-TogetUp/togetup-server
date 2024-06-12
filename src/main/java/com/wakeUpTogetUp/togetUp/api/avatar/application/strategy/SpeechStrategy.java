package com.wakeUpTogetUp.togetUp.api.avatar.application.strategy;

import com.wakeUpTogetUp.togetUp.api.avatar.domain.AvatarSpeech;

public interface SpeechStrategy {

    String makeSpeech(AvatarSpeech avatarSpeech);
}
