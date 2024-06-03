package com.wakeUpTogetUp.togetUp.infra.google.vision;

import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.Feature.Type;
import com.wakeUpTogetUp.togetUp.api.mission.domain.FaceAnnotationRecognitionResult;
import com.wakeUpTogetUp.togetUp.api.mission.domain.VisionAnalysisResult;
import com.wakeUpTogetUp.togetUp.api.mission.service.VisionService;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.infra.google.vision.mapper.GoogleVisionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gcp.vision.CloudVisionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class GoogleVisionService implements VisionService {

    private final CloudVisionTemplate cloudVisionTemplate;

    private final GoogleVisionMapper googleVisionMapper;

    @Override
    public VisionAnalysisResult getResult(MultipartFile file, String target) {
        AnnotateImageResponse response =
                cloudVisionTemplate.analyzeImage(file.getResource(), Type.FACE_DETECTION);

        if (response.getFaceAnnotationsList().isEmpty()) {
            throw new BaseException(Status.NO_DETECTED_OBJECT);
        }

        return FaceAnnotationRecognitionResult.builder()
                .target(target)
                .faceAnnotations(googleVisionMapper.toCustomRecognizedEmotions(response.getFaceAnnotationsList()))
                .build();
    }
}
