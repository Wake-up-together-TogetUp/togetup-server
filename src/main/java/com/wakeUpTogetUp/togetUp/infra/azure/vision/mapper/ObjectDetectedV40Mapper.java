package com.wakeUpTogetUp.togetUp.infra.azure.vision.mapper;

import com.azure.ai.vision.imageanalysis.DetectedObject;
import com.wakeUpTogetUp.togetUp.api.mission.model.BoundingBox;
import com.wakeUpTogetUp.togetUp.api.mission.domain.CustomDetectedObject;
import com.wakeUpTogetUp.togetUp.api.mission.model.Coord;
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
        Coord coord = new Coord(
                detectedObject.getBoundingBox().getX(),
                detectedObject.getBoundingBox().getY());

        return CustomDetectedObject.builder()
                .name(detectedObject.getName())
                .parent(null)
                .confidence(detectedObject.getConfidence())
                .box(BoundingBox.builder()
                        .coord(coord)
                        .w(detectedObject.getBoundingBox().getW())
                        .h(detectedObject.getBoundingBox().getH())
                        .build())
                .build();
    }
}
