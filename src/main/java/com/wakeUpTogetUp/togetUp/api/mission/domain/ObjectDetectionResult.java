package com.wakeUpTogetUp.togetUp.api.mission.domain;

import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.Builder;

public class ObjectDetectionResult extends VisionAnalysisResult {

    private final List<CustomDetectedObject> objects;
    private final List<CustomDetectedTag> tags;
    private final Pattern targetPattern =
            Pattern.compile("(^|\\s)" + targetName.toLowerCase() + "(\\s|$)");
    private final boolean isFail;

    @Builder
    private ObjectDetectionResult(String target, List<CustomDetectedObject> objects, List<CustomDetectedTag> tags) {
        super(target);
        this.objects = objects;
        this.tags = tags;
        this.isFail = determineResult();
    }

    private boolean determineResult() {
        return !hasAnyMatchObject() && !hasAnyMatchTag();
    }

    @Override
    public boolean isFail() {
        return isFail;
    }

    private boolean hasAnyMatchObject() {
        return objects.stream()
            .anyMatch(object -> object.isMatchEntity(targetPattern));
    }

    private boolean hasAnyMatchTag() {
        return tags.stream()
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
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("[미션 결과] : ");

        if (isFail) {
            sb.append("실패");
        } else {
            sb.append("성공");
        }

        sb.append("\ntargetName = ").append(targetName).append("\n");

        sb.append("\n[객체 인식]\n");
        sb.append("objects.size() = ").append(objects.size()).append("\n");
        objects.forEach(object -> {
            sb.append("\n");
            sb.append("object = ").append(object.getName()).append("\n");
            sb.append("parent = ").append(object.getParent()).append("\n");
        });

        sb.append("\n[태그]").append("\n");
        sb.append("tags.size() = ").append(tags.size()).append("\n\n");
        tags.forEach(tag -> sb.append(tag.getName()).append("\n"));

        return sb.toString();
    }
}
