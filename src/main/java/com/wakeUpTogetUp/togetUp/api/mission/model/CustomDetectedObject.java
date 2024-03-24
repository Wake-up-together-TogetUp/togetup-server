package com.wakeUpTogetUp.togetUp.api.mission.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomDetectedObject {

    private String object;

    private String parent;

    private double confidence;

    private BoundingBox box;

    public String getObjectParent() {
        return object + parent;
    }
}
