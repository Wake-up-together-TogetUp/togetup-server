package com.wakeUpTogetUp.togetUp.api.mission.domain;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
public class MissionPerformResult {
    private final int MAX_MATCHES_LIMIT = 3;
    private final String targetObject;

    @Getter
    private final VisionAnalysisResult analysisResult;

    @Builder
    private MissionPerformResult(String object, VisionAnalysisResult analysisResult) {
        this.targetObject = object;
        this.analysisResult = analysisResult;
    }

    public boolean isFail() {
        return analysisResult.isFail(targetObject);
    }

    public List<CustomAnalysisEntity> getMatches() {
        return analysisResult.getMatches(targetObject, MAX_MATCHES_LIMIT);
    }
}
