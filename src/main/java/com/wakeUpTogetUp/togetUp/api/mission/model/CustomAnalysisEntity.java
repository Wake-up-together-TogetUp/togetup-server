package com.wakeUpTogetUp.togetUp.api.mission.model;

import lombok.Getter;

@Getter
public abstract class CustomAnalysisEntity {

    protected final String name;

    private final double confidence;

    private final BoundingBox box;

    public CustomAnalysisEntity(String name, double confidence, BoundingBox box) {
        this.name = name;
        this.confidence = confidence;
        this.box = box;
    }

    public abstract boolean isMatchEntity(String target);
}
