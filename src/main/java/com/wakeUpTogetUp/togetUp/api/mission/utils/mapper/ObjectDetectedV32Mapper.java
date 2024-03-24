package com.wakeUpTogetUp.togetUp.api.mission.utils.mapper;

import com.microsoft.azure.cognitiveservices.vision.computervision.models.DetectedObject;
import com.wakeUpTogetUp.togetUp.api.mission.model.BoundingBox;
import com.wakeUpTogetUp.togetUp.api.mission.model.CustomDetectedObject;
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

        return CustomDetectedObject.builder()
                .object(detectedObject.objectProperty())
                .parent(parent)
                .confidence(detectedObject.confidence())
                .box(BoundingBox.builder()
                        .x(detectedObject.rectangle().x())
                        .y(detectedObject.rectangle().y())
                        .w(detectedObject.rectangle().w())
                        .h(detectedObject.rectangle().h())
                        .build())
                .build();
    }
}
