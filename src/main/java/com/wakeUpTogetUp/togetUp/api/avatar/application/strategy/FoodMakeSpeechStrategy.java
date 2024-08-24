package com.wakeUpTogetUp.togetUp.api.avatar.application.strategy;

import com.wakeUpTogetUp.togetUp.api.avatar.application.RandomFoodProvider;
import com.wakeUpTogetUp.togetUp.api.avatar.domain.AvatarSpeech;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FoodMakeSpeechStrategy implements MakeSpeechStrategy {

    private final RandomFoodProvider randomFoodProvider;

    @Override
    public String makeSpeech(AvatarSpeech avatarSpeech) {
        return String.format(avatarSpeech.getSpeech(), randomFoodProvider.getRandomFoodWord());
    }
}
