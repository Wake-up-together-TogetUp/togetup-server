package com.wakeUpTogetUp.togetUp.api.avatar.application.strategy;

import com.wakeUpTogetUp.togetUp.api.avatar.domain.AvatarSpeech;
import org.springframework.stereotype.Component;

@Component
public class BasicMakeSpeechStrategy implements MakeSpeechStrategy {

    @Override
    public String makeSpeech(AvatarSpeech avatarSpeech) {
        return avatarSpeech.getSpeech();
    }
}
