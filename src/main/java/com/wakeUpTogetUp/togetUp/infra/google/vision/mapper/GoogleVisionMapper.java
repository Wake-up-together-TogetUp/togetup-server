package com.wakeUpTogetUp.togetUp.infra.google.vision.mapper;

import com.google.cloud.vision.v1.FaceAnnotation;
import com.google.cloud.vision.v1.Vertex;
import com.wakeUpTogetUp.togetUp.api.mission.domain.CustomDetectedFaceAnnotation;
import com.wakeUpTogetUp.togetUp.api.mission.model.BoundingBox;
import com.wakeUpTogetUp.togetUp.api.mission.model.Coord;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class GoogleVisionMapper {

    public List<CustomDetectedFaceAnnotation> toCustomRecognizedEmotions(List<FaceAnnotation> faceAnnotations) {
        return faceAnnotations.stream()
                .map(this::toCustomRecognizedEmotion)
                .collect(Collectors.toList());
    }

    private CustomDetectedFaceAnnotation toCustomRecognizedEmotion(FaceAnnotation faceAnnotation) {
        return CustomDetectedFaceAnnotation.builder()
                .joyLikelihood(faceAnnotation.getJoyLikelihoodValue())
                .sorrowLikelihood(faceAnnotation.getSorrowLikelihoodValue())
                .angerLikelihood(faceAnnotation.getAngerLikelihoodValue())
                .surpriseLikelihood(faceAnnotation.getSurpriseLikelihoodValue())
                .confidence(faceAnnotation.getDetectionConfidence())
                .box(toBoundingBox(faceAnnotation.getBoundingPoly().getVerticesList()))
                .build();
    }

    private BoundingBox toBoundingBox(List<Vertex> vertexs) {
        int x1 = vertexs.get(0).getX();
        int y1 = vertexs.get(0).getY();
        int x2 = vertexs.get(2).getX();
        int y2 = vertexs.get(2).getY();

        Coord coord = new Coord(x1, y1);
        int width = x2 - x1;
        int height = y2 - y1;

        return BoundingBox.builder()
                .coord(coord)
                .w(width)
                .h(height)
                .build();
    }
}
