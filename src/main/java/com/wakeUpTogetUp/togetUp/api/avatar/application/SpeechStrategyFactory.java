package com.wakeUpTogetUp.togetUp.api.avatar.application;

import com.wakeUpTogetUp.togetUp.api.avatar.application.strategy.BasicSpeechStrategy;
import com.wakeUpTogetUp.togetUp.api.avatar.application.strategy.FoodSpeechStrategy;
import com.wakeUpTogetUp.togetUp.api.avatar.application.strategy.SpeechStrategy;
import com.wakeUpTogetUp.togetUp.api.avatar.domain.AvatarSpeechCondition;
import java.util.EnumMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpeechStrategyFactory {
    private static final Map<AvatarSpeechCondition, SpeechStrategy> strategies = new EnumMap<>(AvatarSpeechCondition.class);
    private final BasicSpeechStrategy basicSpeechStrategy;
    private final FoodSpeechStrategy foodSpeechStrategy;

    @PostConstruct
    public void init() {
        strategies.put(AvatarSpeechCondition.DEFAULT, basicSpeechStrategy);
        strategies.put(AvatarSpeechCondition.NONE, basicSpeechStrategy);
        strategies.put(AvatarSpeechCondition.FOOD, foodSpeechStrategy);
    }

    public SpeechStrategy getStrategy(AvatarSpeechCondition condition) {
        return strategies.getOrDefault(condition, new BasicSpeechStrategy());
    }
}
