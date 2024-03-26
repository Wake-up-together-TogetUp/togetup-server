package com.wakeUpTogetUp.togetUp.infra.google.vision;

import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.FaceAnnotation;
import com.google.cloud.vision.v1.Feature.Type;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gcp.vision.CloudVisionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class GoogleVisionService {
    private final CloudVisionTemplate cloudVisionTemplate;

    public List<FaceAnnotation> getFaceRecognitionResult(MultipartFile file) {
        AnnotateImageResponse response =
                this.cloudVisionTemplate.analyzeImage(file.getResource(), Type.FACE_DETECTION);

        return response.getFaceAnnotationsList();
    }
}
