package com.wakeUpTogetUp.togetUp.api.avatar.application;

import com.wakeUpTogetUp.togetUp.api.avatar.application.strategy.BasicMakeSpeechStrategy;
import com.wakeUpTogetUp.togetUp.api.avatar.application.strategy.FoodMakeSpeechStrategy;
import com.wakeUpTogetUp.togetUp.api.avatar.application.strategy.MakeSpeechStrategy;
import com.wakeUpTogetUp.togetUp.api.avatar.application.strategy.TimeMakeSpeechStrategy;
import com.wakeUpTogetUp.togetUp.api.avatar.domain.AvatarSpeechCondition;
import java.util.EnumMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MakeSpeechStrategyFactory {
    private static final Map<AvatarSpeechCondition, MakeSpeechStrategy> strategies = new EnumMap<>(AvatarSpeechCondition.class);
    private final BasicMakeSpeechStrategy basicSpeechStrategy;
    private final FoodMakeSpeechStrategy foodSpeechStrategy;
    private final TimeMakeSpeechStrategy timeSpeechStrategy;

    @PostConstruct
    public void init() {
        strategies.put(AvatarSpeechCondition.DEFAULT, basicSpeechStrategy);
        strategies.put(AvatarSpeechCondition.NONE, basicSpeechStrategy);
        strategies.put(AvatarSpeechCondition.FOOD, foodSpeechStrategy);
        strategies.put(AvatarSpeechCondition.TIME, timeSpeechStrategy);
    }

    public MakeSpeechStrategy getStrategy(AvatarSpeechCondition condition) {
        return strategies.getOrDefault(condition, new BasicMakeSpeechStrategy());
    }
}
