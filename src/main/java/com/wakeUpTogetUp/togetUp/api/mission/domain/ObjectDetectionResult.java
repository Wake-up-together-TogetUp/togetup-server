package com.wakeUpTogetUp.togetUp.api.mission.domain;

import com.wakeUpTogetUp.togetUp.api.mission.model.CustomAnalysisEntity;
import com.wakeUpTogetUp.togetUp.api.mission.model.CustomDetectedObject;
import com.wakeUpTogetUp.togetUp.api.mission.model.CustomDetectedTag;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ObjectDetectionResult extends VisionAnalysisResult {

    private final List<CustomDetectedObject> objects;

    private final List<CustomDetectedTag> tags;

    @Builder
    private ObjectDetectionResult(List<CustomDetectedObject> objects, List<CustomDetectedTag> tags) {
        this.objects = objects;
        this.tags = tags;
    }

    @Override
    public List<CustomAnalysisEntity> getEntities() {
        return objects.stream()
                .map(CustomAnalysisEntity.class::cast)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isFail(String targetObject) {
        return tags.stream()
                .noneMatch(tag -> tag.isMatchEntity(targetObject));
    }

    @Override
    public List<CustomAnalysisEntity> getMatches(String targetObject, int size) {
        return objects.stream()
                .filter(object -> object.isMatchEntity(targetObject))
                .sorted(Comparator.comparing(CustomDetectedObject::getConfidence).reversed())
                .limit(size)
                .collect(Collectors.toList());
    }


    // TODO: 디버그용 검증 후 삭제요망
    @Override
    public void print() {
        printDetectedObjects();
        printDetectedTags();
    }

    private void printDetectedObjects() {
        System.out.println("detectedObjects.size() = " + objects.size());
        objects.forEach(object -> System.out.println(object.getName()));
        objects.forEach(object -> System.out.println(object.getName()));
    }

    private void printDetectedTags() {
        System.out.println("tags.size() = " + tags.size());
        tags.forEach(tag -> System.out.println(tag.getName()));
    }
}
