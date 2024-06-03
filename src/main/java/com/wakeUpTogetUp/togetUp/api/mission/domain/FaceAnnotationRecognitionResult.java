package com.wakeUpTogetUp.togetUp.api.mission.domain;

import com.wakeUpTogetUp.togetUp.api.mission.model.Expression;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Builder;

public class FaceAnnotationRecognitionResult extends VisionAnalysisResult {

    private final List<CustomDetectedFaceAnnotation> faceAnnotations;

    private Expression targetExpression;

    @Builder
    public FaceAnnotationRecognitionResult(String target, List<CustomDetectedFaceAnnotation> faceAnnotations) {
        super(target);
        this.faceAnnotations = faceAnnotations;
        initTarget();
    }

    @Override
    void initTarget() {
        this.targetExpression = Expression.fromName(targetName);
    }

    @Override
    public boolean isFail() {
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
    public void print() {
        System.out.println("\n\ntargetName = " + targetName);
        System.out.println("[실행시간]: " + LocalDateTime.now());

        System.out.println("\n\n[표정 인식] " + LocalDateTime.now());
        System.out.println("emotions.size() = " + faceAnnotations.size());
        System.out.println();

        faceAnnotations.forEach(faceAnnotation -> {
            System.out.println("JOY = " + faceAnnotation.getJoyLikelihood());
            System.out.println("SORROW = " + faceAnnotation.getSorrowLikelihood());
            System.out.println("ANGER = " + faceAnnotation.getAngerLikelihood());
            System.out.println("SURPRISE = " + faceAnnotation.getSurpriseLikelihood());
            System.out.println();
        });
    }
}
