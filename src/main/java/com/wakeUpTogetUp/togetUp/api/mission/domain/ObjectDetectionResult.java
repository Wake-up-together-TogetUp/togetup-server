package com.wakeUpTogetUp.togetUp.api.mission.domain;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;

public class ObjectDetectionResult extends VisionAnalysisResult {

    private final List<CustomDetectedObject> objects;
    private final List<CustomDetectedTag> tags;

    @Builder
    private ObjectDetectionResult(String target, List<CustomDetectedObject> objects, List<CustomDetectedTag> tags) {
        super(target);
        this.objects = objects;
        this.tags = tags;
    }

    @Override
    public boolean isFail() {
        return !hasAnyMatchTag() && !hasAnyMatchObject();
    }

    private boolean hasAnyMatchTag() {
        return tags.stream()
                .anyMatch(tag -> tag.isMatchEntity(targetName));
    }

    private boolean hasAnyMatchObject() {
        return objects.stream()
                .anyMatch(object -> object.isMatchEntity(targetName));
    }

    @Override
    public List<CustomAnalysisEntity> getEntities() {
        return objects.stream()
                .map(CustomAnalysisEntity.class::cast)
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomAnalysisEntity> getMatches(int size) {
        return objects.stream()
                .filter(object -> object.isMatchEntity(targetName))
                .sorted(Comparator.comparing(CustomDetectedObject::getConfidence).reversed())
                .limit(size)
                .collect(Collectors.toList());
    }

    @Override
    public void print() {
        printDetectedObjects();
        printDetectedTags();
    }

    private void printDetectedObjects() {
        System.out.println("\n[OBJECT]");
        System.out.println("objects.size() = " + objects.size());
        objects.forEach(object -> {
            System.out.println();
            System.out.println("object = " + object.getName());
            System.out.println("parent = " + object.getParent());});
    }

    private void printDetectedTags() {
        System.out.println("\n[TAG]");
        System.out.println("tags.size() = " + tags.size());
        System.out.println();
        tags.forEach(tag -> System.out.println(tag.getName()));
    }
}
