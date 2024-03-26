package com.wakeUpTogetUp.togetUp.api.mission.domain;

import com.wakeUpTogetUp.togetUp.api.mission.model.BoundingBox;
import lombok.Getter;

@Getter
public abstract class CustomAnalysisEntity {

    protected final String targetName;

    private final double confidence;

    private final BoundingBox box;

    public CustomAnalysisEntity(String targetName, double confidence, BoundingBox box) {
        this.targetName = targetName;
        this.confidence = confidence;
        this.box = box;
    }

    public abstract boolean isMatchEntity(String target);
}
