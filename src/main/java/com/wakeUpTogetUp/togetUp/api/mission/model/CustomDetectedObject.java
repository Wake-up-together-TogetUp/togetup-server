package com.wakeUpTogetUp.togetUp.api.mission.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CustomDetectedObject extends CustomAnalysisEntity {

    private String parent;

    @Builder
    private CustomDetectedObject(String object, String parent, double confidence, BoundingBox box) {
        super(object, confidence, box);
        this.parent = parent;
    }

    @Override
    public boolean isMatchEntity(String targetObject) {
        return concatObjectAndParent().toLowerCase().contains(targetObject);
    }

    public String concatObjectAndParent() {
        return name + parent;
    }

}
