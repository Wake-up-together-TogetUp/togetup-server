package com.wakeUpTogetUp.togetUp.infra.azure.vision;

import com.microsoft.azure.cognitiveservices.vision.computervision.ComputerVisionClient;
import com.microsoft.azure.cognitiveservices.vision.computervision.models.ImageAnalysis;
import com.microsoft.azure.cognitiveservices.vision.computervision.models.VisualFeatureTypes;
import com.wakeUpTogetUp.togetUp.api.mission.model.CustomDetectedObject;
import com.wakeUpTogetUp.togetUp.api.mission.service.mapper.ObjectDetectedV32Mapper;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import java.io.IOException;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AzureVision32Service {

    private final ComputerVisionClient client;
    private final ObjectDetectedV32Mapper mapper;

    public List<CustomDetectedObject> detectObjects(MultipartFile file) {
        List<VisualFeatureTypes> features = List.of(VisualFeatureTypes.OBJECTS);

        try {
            ImageAnalysis analysis = client.computerVision()
                    .analyzeImageInStream()
                    .withImage(file.getBytes())
                    .withVisualFeatures(features)
                    .execute();

            if (analysis.objects() == null) {
                throw new BaseException(Status.NO_DETECTED_OBJECT);
            }

            return mapper.toCustomDetectedObjects(analysis.objects());
        } catch (IOException e) {
            throw new BaseException(Status.INVALID_IMAGE);
        }
    }
}