package com.wakeUpTogetUp.togetUp.api.mission.domain;

import com.wakeUpTogetUp.togetUp.api.mission.model.BoundingBox;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CustomDetectedObject extends CustomAnalysisEntity {

    private final String parent;

    @Builder
    private CustomDetectedObject(String objectName, String parent, double confidence, BoundingBox box) {
        super(objectName, confidence, box);
        this.parent = parent;
    }

    @Override
    public boolean isMatchEntity(String targetObject) {
        return concatObjectAndParent().toLowerCase().contains(targetObject.toLowerCase());
    }

    public String concatObjectAndParent() {
        return targetName + parent;
    }

}
