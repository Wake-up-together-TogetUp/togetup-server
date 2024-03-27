package com.wakeUpTogetUp.togetUp.api.mission.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CustomDetectedTag extends CustomAnalysisEntity {

    private final String name;

    @Builder
    public CustomDetectedTag(String name, double confidence) {
        super(confidence, null);
        this.name = name;
    }

    @Override
    protected boolean isMatchEntity(Object target) {
        return name.toLowerCase()
                .contains(target.toString().toLowerCase());
    }
}
