package com.wakeUpTogetUp.togetUp.api.mission.domain;

import com.wakeUpTogetUp.togetUp.api.mission.model.BoundingBox;
import com.wakeUpTogetUp.togetUp.api.mission.model.Expression;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CustomDetectedFaceAnnotation extends CustomAnalysisEntity {

    private final int joyLikelihood;
    private final int sorrowLikelihood;
    private final int angerLikelihood;
    private final int surpriseLikelihood;

    @Builder
    private CustomDetectedFaceAnnotation(double confidence, BoundingBox box, int joyLikelihood,
            int sorrowLikelihood, int angerLikelihood, int surpriseLikelihood) {
        super(confidence, box);
        this.joyLikelihood = joyLikelihood;
        this.sorrowLikelihood = sorrowLikelihood;
        this.angerLikelihood = angerLikelihood;
        this.surpriseLikelihood = surpriseLikelihood;
    }

    @Override
    protected boolean isMatchEntity(Object target) {
        return getLikelihood((Expression) target) >= 3;
    }

    private int getLikelihood(Expression expression) {
        switch (expression) {
            case JOY:
                return joyLikelihood;
            case SORROW:
                return sorrowLikelihood;
            case ANGER:
                return angerLikelihood;
            case SURPRISE:
                return surpriseLikelihood;
            default:
                throw new BaseException(Status.INVALID_OBJECT_NAME);
        }
    }
}
