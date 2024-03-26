package com.wakeUpTogetUp.togetUp.api.mission.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CustomDetectedTag extends CustomAnalysisEntity {
    @Builder
    public CustomDetectedTag(String tagName, double confidence) {
        super(tagName, confidence, null);
    }

    @Override
    public boolean isMatchEntity(String target) {
        return targetName.toLowerCase().contains(target.toLowerCase());
    }
}
