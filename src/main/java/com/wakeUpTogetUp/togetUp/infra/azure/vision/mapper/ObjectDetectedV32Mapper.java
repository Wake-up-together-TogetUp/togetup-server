package com.wakeUpTogetUp.togetUp.infra.azure.vision.mapper;

import com.microsoft.azure.cognitiveservices.vision.computervision.models.DetectedObject;
import com.wakeUpTogetUp.togetUp.api.mission.model.BoundingBox;
import com.wakeUpTogetUp.togetUp.api.mission.domain.CustomDetectedObject;
import com.wakeUpTogetUp.togetUp.api.mission.model.Coord;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ObjectDetectedV32Mapper {

    public List<CustomDetectedObject> toCustomDetectedObjects(List<DetectedObject> objects) {
        return objects.stream()
                .map(this::toCustomDetectedObject)
                .collect(Collectors.toList());
    }

    private CustomDetectedObject toCustomDetectedObject(DetectedObject detectedObject) {
        String parent = detectedObject.parent() != null
                ? detectedObject.parent().objectProperty()
                : null;
        Coord coord = new Coord(
                detectedObject.rectangle().x(),
                detectedObject.rectangle().y());

        return CustomDetectedObject.builder()
                .name(detectedObject.objectProperty())
                .parent(parent)
                .confidence(detectedObject.confidence())
                .box(BoundingBox.builder()
                        .coord(coord)
                        .w(detectedObject.rectangle().w())
                        .h(detectedObject.rectangle().h())
                        .build())
                .build();
    }
}
