package com.wakeUpTogetUp.togetUp.api.mission.strategy;

import com.wakeUpTogetUp.togetUp.api.mission.model.MissionType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MissionImageStrategyFactory {

    private final SimpleConversionStrategy simpleConversionStrategy;
    private final AnalysisConversionStrategy analysisConversionStrategy;

    public MissionImageStrategy getStrategy(MissionType missionType) {
        switch (missionType) {
            case DIRECT_REGISTRATION:
                return simpleConversionStrategy;

            default:
                return analysisConversionStrategy;
        }
    }
}
