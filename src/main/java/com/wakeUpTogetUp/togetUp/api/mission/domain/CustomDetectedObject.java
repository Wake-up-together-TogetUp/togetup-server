package com.wakeUpTogetUp.togetUp.api.mission.domain;

import com.wakeUpTogetUp.togetUp.api.mission.model.BoundingBox;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CustomDetectedObject extends CustomAnalysisEntity {

    private final String name;
    private final String parent;

    @Builder
    private CustomDetectedObject(String name, String parent, double confidence, BoundingBox box) {
        super(confidence, box);
        this.name = name;
        this.parent = parent;
    }

    @Override
    public boolean isMatchEntity(Object target) {
        return concatObjectAndParent()
                .contains(target.toString().toLowerCase());
    }

    public String concatObjectAndParent() {
        return (name + parent).toLowerCase();
    }
}
