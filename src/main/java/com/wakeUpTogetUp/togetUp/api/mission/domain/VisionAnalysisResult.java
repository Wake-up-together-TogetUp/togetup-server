package com.wakeUpTogetUp.togetUp.api.mission.domain;

import com.wakeUpTogetUp.togetUp.api.mission.model.CustomAnalysisEntity;
import java.util.List;

public abstract class VisionAnalysisResult {

    public abstract List<CustomAnalysisEntity> getEntities();

    protected abstract boolean isFail(String object);

    protected abstract List<CustomAnalysisEntity> getMatches(String object, int size);

    // TODO: 디버그용 삭제요망
    public abstract void print();
}
