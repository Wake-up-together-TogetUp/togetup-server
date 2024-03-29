package com.wakeUpTogetUp.togetUp.api.mission.domain;

import com.wakeUpTogetUp.togetUp.api.mission.model.Expression;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;

public class FaceAnnotationRecognitionResult extends VisionAnalysisResult {

    private final Expression expression;
    private final List<CustomDetectedFaceAnnotation> faceAnnotations;

    @Builder
    public FaceAnnotationRecognitionResult(String target, List<CustomDetectedFaceAnnotation> faceAnnotations) {
        super(target);
        this.expression = Expression.fromName(targetName);
        this.faceAnnotations = faceAnnotations;
    }

    @Override
    public boolean isFail() {
        return faceAnnotations.stream()
                .noneMatch(faceAnnotation -> faceAnnotation.isMatchEntity(this.expression));
    }

    @Override
    public List<CustomAnalysisEntity> getEntities() {
        return faceAnnotations.stream()
                .map(CustomAnalysisEntity.class::cast)
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomAnalysisEntity> getMatches(int size) {
        return faceAnnotations.stream()
                .filter(faceAnnotation -> faceAnnotation.isMatchEntity(this.expression))
                .sorted(Comparator.comparing(CustomDetectedFaceAnnotation::getConfidence).reversed())
                .limit(size)
                .collect(Collectors.toList());
    }

    @Override
    public void print() {
        System.out.println("\n[FACE ANNOTATION]");
        System.out.println("emotions.size() = " + faceAnnotations.size());

        faceAnnotations.forEach(faceAnnotation -> {
            System.out.println("JOY = " + faceAnnotation.getJoyLikelihood());
            System.out.println("SORROW = " + faceAnnotation.getSorrowLikelihood());
            System.out.println("ANGER = " + faceAnnotation.getAngerLikelihood());
            System.out.println("SURPRISE = " + faceAnnotation.getSurpriseLikelihood());
            System.out.println();
        });
    }
}
