package com.wakeUpTogetUp.togetUp.api.mission.domain;

import com.wakeUpTogetUp.togetUp.api.mission.model.BoundingBox;
import lombok.Getter;

@Getter
public abstract class CustomAnalysisEntity {

    private final double confidence;

    private final BoundingBox box;

    protected CustomAnalysisEntity(double confidence, BoundingBox box) {
        this.confidence = confidence;
        this.box = box;
    }

    protected abstract boolean isMatchEntity(Object target);
}
