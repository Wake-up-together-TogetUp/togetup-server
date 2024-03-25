package com.wakeUpTogetUp.togetUp.api.mission.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CustomDetectedTag extends CustomAnalysisEntity {
    @Builder
    public CustomDetectedTag(String name, double confidence) {
        super(name, confidence, new BoundingBox());
    }

    @Override
    public boolean isMatchEntity(String target) {
        return name.toLowerCase().contains(target);
    }
}
