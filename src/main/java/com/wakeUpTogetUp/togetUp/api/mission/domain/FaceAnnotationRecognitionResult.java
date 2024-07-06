package com.wakeUpTogetUp.togetUp.api.mission.domain;

import com.wakeUpTogetUp.togetUp.api.mission.model.Expression;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;

public class FaceAnnotationRecognitionResult extends VisionAnalysisResult {

    private final List<CustomDetectedFaceAnnotation> faceAnnotations;
    private final Expression targetExpression = Expression.fromName(targetName);
    private final boolean isFail;

    @Builder
    public FaceAnnotationRecognitionResult(String target, List<CustomDetectedFaceAnnotation> faceAnnotations) {
        super(target);
        this.faceAnnotations = faceAnnotations;
        this.isFail = determineResult();
    }

    @Override
    public boolean isFail() {
        return isFail;
    }

    private boolean determineResult() {
        return faceAnnotations.stream()
                .noneMatch(faceAnnotation -> faceAnnotation.isMatchEntity(this.targetExpression));
    }

    @Override
    public List<CustomAnalysisEntity> getMatches(int size) {
        return faceAnnotations.stream()
                .filter(faceAnnotation -> faceAnnotation.isMatchEntity(this.targetExpression))
                .sorted(Comparator.comparing(CustomDetectedFaceAnnotation::getConfidence).reversed())
                .limit(size)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("[미션 결과] : ");

        if (isFail) {
            sb.append("실패");
        } else {
            sb.append("성공");
        }

        sb.append("\ntargetName = ").append(targetName).append("\n");

        sb.append("\n[표정 인식]\n");
        sb.append("emotions.size() = ").append(faceAnnotations.size()).append("\n\n");

        faceAnnotations.forEach(faceAnnotation -> {
            sb.append("JOY = ").append(faceAnnotation.getJoyLikelihood()).append("\n");
            sb.append("SORROW = ").append(faceAnnotation.getSorrowLikelihood()).append("\n");
            sb.append("ANGER = ").append(faceAnnotation.getAngerLikelihood()).append("\n");
            sb.append("SURPRISE = ").append(faceAnnotation.getSurpriseLikelihood()).append("\n");
            sb.append("\n");
        });

        return sb.toString();
    }
}
