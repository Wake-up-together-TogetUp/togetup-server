package com.wakeUpTogetUp.togetUp.api.avatar.application.strategy;

import com.wakeUpTogetUp.togetUp.api.avatar.domain.AvatarSpeech;
import com.wakeUpTogetUp.togetUp.utils.DateTimeProvider;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class TimeSpeechStrategy implements SpeechStrategy{

    @Override
    public String makeSpeech(AvatarSpeech avatarSpeech) {
        LocalDateTime now = DateTimeProvider.getCurrentDateTimeInSeoul();
        return String.format(avatarSpeech.getSpeech(), now.getHour(), now.getMinute());
    }
}
