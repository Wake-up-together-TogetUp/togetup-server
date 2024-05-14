package com.wakeUpTogetUp.togetUp.infra.azure.vision;

import com.microsoft.azure.cognitiveservices.vision.computervision.ComputerVisionClient;
import com.microsoft.azure.cognitiveservices.vision.computervision.models.ImageAnalysis;
import com.microsoft.azure.cognitiveservices.vision.computervision.models.VisualFeatureTypes;
import com.wakeUpTogetUp.togetUp.api.mission.domain.ObjectDetectionResult;
import com.wakeUpTogetUp.togetUp.api.mission.domain.VisionAnalysisResult;
import com.wakeUpTogetUp.togetUp.api.mission.service.VisionService;
import com.wakeUpTogetUp.togetUp.infra.azure.vision.mapper.ObjectDetectedV32Mapper;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.infra.azure.vision.mapper.TagDetectedV32Mapper;
import com.wakeUpTogetUp.togetUp.utils.image.ImageProcessor;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AzureVision32Service implements VisionService {

    private final static int IMAGE_SIZE_LIMIT = 4;

    private final ComputerVisionClient client;
    private final ObjectDetectedV32Mapper objectDetectedV32Mapper;
    private final TagDetectedV32Mapper tagDetectedV32Mapper;

    public VisionAnalysisResult getResult(MultipartFile file, String target) {
        try {
            byte[] data = ImageProcessor.compressUntil(file, IMAGE_SIZE_LIMIT);

            ImageAnalysis analysis = getAnalysisResult(data);
            validateNotDetected(analysis);

            return ObjectDetectionResult.builder()
                    .target(target)
                    .objects(objectDetectedV32Mapper.toCustomDetectedObjects(analysis.objects()))
                    .tags(tagDetectedV32Mapper.toCustomDetectedTags(analysis.tags()))
                    .build();
        } catch (Exception e) {
            throw new BaseException(Status.INTERNAL_SERVER_ERROR);
        }
    }

    private ImageAnalysis getAnalysisResult(byte[] data) {
        List<VisualFeatureTypes> features =
                List.of(VisualFeatureTypes.OBJECTS, VisualFeatureTypes.TAGS);

        return client.computerVision()
                .analyzeImageInStream()
                .withImage(data)
                .withVisualFeatures(features)
                .execute();
    }

    private void validateNotDetected(ImageAnalysis analysis) {
        if (analysis.objects() == null && analysis.tags() == null) {
            throw new BaseException(Status.NO_DETECTED_OBJECT);
        }
    }
}