package com.wakeUpTogetUp.togetUp.api.mission.service.mapper;

import com.azure.ai.vision.imageanalysis.DetectedObject;
import com.wakeUpTogetUp.togetUp.api.mission.model.BoundingBox;
import com.wakeUpTogetUp.togetUp.api.mission.model.CustomDetectedObject;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ObjectDetectedV40Mapper {
    public List<CustomDetectedObject> toCustomDetectedObjects(List<DetectedObject> objects) {
        return objects.stream()
                .map(this::toCustomDetectedObject)
                .collect(Collectors.toList());
    }

    private CustomDetectedObject toCustomDetectedObject(DetectedObject detectedObject) {
        return CustomDetectedObject.builder()
                .object(detectedObject.getName())
                .parent(null)
                .confidence(detectedObject.getConfidence())
                .box(BoundingBox.builder()
                        .x(detectedObject.getBoundingBox().getX())
                        .y(detectedObject.getBoundingBox().getY())
                        .w(detectedObject.getBoundingBox().getW())
                        .h(detectedObject.getBoundingBox().getH())
                        .build())
                .build();
    }
}
