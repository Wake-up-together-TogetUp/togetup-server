package com.wakeUpTogetUp.togetUp.api.mission.domain;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import lombok.Builder;

public class ObjectDetectionResult extends VisionAnalysisResult {

    private final List<CustomDetectedObject> objects;
    private final List<CustomDetectedTag> tags;

    protected Pattern targetPattern;

    @Builder
    private ObjectDetectionResult(String target, List<CustomDetectedObject> objects, List<CustomDetectedTag> tags) {
        super(target);
        this.objects = objects;
        this.tags = tags;
        initTarget();
    }

    @Override
    void initTarget() {
        this.targetPattern = Pattern.compile("(^|\\s)" + targetName.toLowerCase() + "(\\s|$)");
    }

    @Override
    public boolean isFail() {
        return !hasAnyMatchObject() && !hasAnyMatchTag();
    }

    private boolean hasAnyMatchObject() {
        return objects.stream()
            .peek(object -> System.out.println("Checking object: " + object.getName() + ", isMatchEntity: " + object.isMatchEntity(targetPattern)))
            .anyMatch(object -> object.isMatchEntity(targetPattern));
    }

    private boolean hasAnyMatchTag() {
        return tags.stream()
                .peek(tag -> System.out.println("Checking tag: " + tag.getName() + ", isMatchEntity: " + tag.isMatchEntity(targetPattern)))
                .anyMatch(tag -> tag.isMatchEntity(targetPattern));
    }

    @Override
    public List<CustomAnalysisEntity> getMatches(int size) {
        return objects.stream()
                .filter(object -> object.isMatchEntity(targetPattern))
                .sorted(Comparator.comparing((CustomDetectedObject object) -> !(object instanceof CustomDetectedObject))
                .thenComparing(CustomDetectedObject::getConfidence).reversed())
                .limit(size)
                .collect(Collectors.toList());
    }

    @Override
    public void print() {
        System.out.println("\n\ntargetName = " + targetName);
        System.out.println("[실행시간]: " + LocalDateTime.now());
        printDetectedObjects();
        printDetectedTags();
    }

    private void printDetectedObjects() {
        System.out.println("\n[객체 인식]");
        System.out.println("objects.size() = " + objects.size());
        System.out.println();

        objects.forEach(object -> {
            System.out.println();
            System.out.println("object = " + object.getName());
            System.out.println("parent = " + object.getParent());});
    }

    private void printDetectedTags() {
        System.out.println("\n[태그]");
        System.out.println("tags.size() = " + tags.size());
        System.out.println();

        tags.forEach(tag -> System.out.println(tag.getName()));
    }
}
