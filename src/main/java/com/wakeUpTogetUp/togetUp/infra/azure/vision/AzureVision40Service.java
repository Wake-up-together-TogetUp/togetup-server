package com.wakeUpTogetUp.togetUp.infra.azure.vision;

import com.azure.ai.vision.common.ImageSourceBuffer;
import com.azure.ai.vision.common.ImageWriter;
import com.azure.ai.vision.common.VisionServiceOptions;
import com.azure.ai.vision.common.VisionSource;
import com.azure.ai.vision.imageanalysis.ContentTag;
import com.azure.ai.vision.imageanalysis.DetectedObject;
import com.azure.ai.vision.imageanalysis.ImageAnalysisErrorDetails;
import com.azure.ai.vision.imageanalysis.ImageAnalysisOptions;
import com.azure.ai.vision.imageanalysis.ImageAnalysisResult;
import com.azure.ai.vision.imageanalysis.ImageAnalysisResultDetails;
import com.azure.ai.vision.imageanalysis.ImageAnalysisResultReason;
import com.azure.ai.vision.imageanalysis.ImageAnalyzer;
import com.wakeUpTogetUp.togetUp.api.mission.domain.ObjectDetectionResult;
import com.wakeUpTogetUp.togetUp.api.mission.service.mapper.ObjectDetectedV40Mapper;
import com.wakeUpTogetUp.togetUp.api.mission.service.mapper.TagDetectedV40Mapper;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import java.io.IOException;
import java.nio.ByteBuffer;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AzureVision40Service {

    private final VisionServiceOptions serviceOptions;
    private final ImageAnalysisOptions analysisOptions;
    private final ObjectDetectedV40Mapper objectDetectedV40Mapper;
    private final TagDetectedV40Mapper tagDetectedV40Mapper;

    public ObjectDetectionResult detect(MultipartFile file) throws Exception {
        VisionSource imageSource = getVisionSource(file);

        try (ImageAnalyzer analyzer = new ImageAnalyzer(serviceOptions, imageSource, analysisOptions);
                ImageAnalysisResult result = analyzer.analyze()) {

            if (result.getReason() != ImageAnalysisResultReason.ANALYZED) {
                ImageAnalysisErrorDetails errorDetails = ImageAnalysisErrorDetails.fromResult(result);
                printErrorDetails(errorDetails);

                throw new BaseException(Status.IMAGE_ANALYSIS_FAIL);
            }

            printDetectionResult(result);
            printResultDetails(ImageAnalysisResultDetails.fromResult(result));

            return ObjectDetectionResult.builder()
                    .objects(objectDetectedV40Mapper.toCustomDetectedObjects(result.getObjects()))
                    .tags(tagDetectedV40Mapper.customDetectedTags(result.getTags()))
                    .build();
        }
    }

    private VisionSource getVisionSource(MultipartFile file) throws IOException {
        ImageSourceBuffer imageSourceBuffer = new ImageSourceBuffer();

        try (ImageWriter imageWriter = imageSourceBuffer.getWriter()) {
            imageWriter.write(ByteBuffer.wrap(file.getBytes()));
        }

        return VisionSource.fromImageSourceBuffer(imageSourceBuffer);
    }

    private void printDetectionResult(ImageAnalysisResult result) {
        System.out.println(" Image height = " + result.getImageHeight());
        System.out.println(" Image width = " + result.getImageWidth());
        System.out.println(" Model version = " + result.getModelVersion());

        if (result.getObjects() != null) {
            System.out.println(" Objects:");
            for (DetectedObject detectedObject : result.getObjects()) {
                System.out.println(detectedObject.toString());
            }
        }

        if (result.getTags() != null) {
            System.out.println(" Tags:");
            for (ContentTag tag : result.getTags()) {
                System.out.println(tag.toString());
            }
        }
    }

    private void printResultDetails(ImageAnalysisResultDetails resultDetails) {
        System.out.println(" Result details:");
        System.out.println("   Image ID = " + resultDetails.getImageId());
        System.out.println("   Result ID = " + resultDetails.getResultId());
        System.out.println("   Connection URL = " + resultDetails.getConnectionUrl());
        System.out.println("   JSON result = " + resultDetails.getJsonResult());
    }

    private void printErrorDetails(ImageAnalysisErrorDetails errorDetails) {
        System.out.println(" Analysis failed.");
        System.out.println("   Error reason: " + errorDetails.getReason());
        System.out.println("   Error code: " + errorDetails.getErrorCode());
        System.out.println("   Error message: " + errorDetails.getMessage());
    }
}
