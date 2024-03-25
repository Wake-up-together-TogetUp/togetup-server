package com.wakeUpTogetUp.togetUp.infra.azure.vision;

import com.azure.ai.vision.common.ImageSourceBuffer;
import com.azure.ai.vision.common.ImageWriter;
import com.azure.ai.vision.common.VisionServiceOptions;
import com.azure.ai.vision.common.VisionSource;
import com.azure.ai.vision.imageanalysis.DetectedObject;
import com.azure.ai.vision.imageanalysis.ImageAnalysisErrorDetails;
import com.azure.ai.vision.imageanalysis.ImageAnalysisFeature;
import com.azure.ai.vision.imageanalysis.ImageAnalysisOptions;
import com.azure.ai.vision.imageanalysis.ImageAnalysisResult;
import com.azure.ai.vision.imageanalysis.ImageAnalysisResultDetails;
import com.azure.ai.vision.imageanalysis.ImageAnalysisResultReason;
import com.azure.ai.vision.imageanalysis.ImageAnalyzer;
import com.wakeUpTogetUp.togetUp.api.mission.model.CustomDetectedObject;
import com.wakeUpTogetUp.togetUp.api.mission.model.CustomDetectedTag;
import com.wakeUpTogetUp.togetUp.api.mission.service.mapper.ObjectDetectedV40Mapper;
import com.wakeUpTogetUp.togetUp.api.mission.service.mapper.TagDetectedV40Mapper;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.EnumSet;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AzureVision40Service {

    private final VisionServiceOptions serviceOptions;
    private final ObjectDetectedV40Mapper objectDetectedV40Mapper;
    private final TagDetectedV40Mapper tagDetectedV40Mapper;

    public List<CustomDetectedObject> detectObjects(MultipartFile file) throws Exception {
        VisionSource imageSource = getVisionSource(file);
        ImageAnalysisOptions analysisOptions = getAnalysisOptions(ImageAnalysisFeature.OBJECTS);

        try (ImageAnalyzer analyzer = new ImageAnalyzer(serviceOptions, imageSource, analysisOptions);
             ImageAnalysisResult result = analyzer.analyze()) {

            if (result.getReason() != ImageAnalysisResultReason.ANALYZED) {
                ImageAnalysisErrorDetails errorDetails = ImageAnalysisErrorDetails.fromResult(result);
                printErrorDetails(errorDetails);

                throw new BaseException(Status.IMAGE_ANALYSIS_FAIL);
            }

            printObjectDetectionResult(result);
            printResultDetails(ImageAnalysisResultDetails.fromResult(result));

            return objectDetectedV40Mapper.toCustomDetectedObjects(result.getObjects());
        }
    }

    public List<CustomDetectedTag> detectTags(MultipartFile file) throws Exception {
        VisionSource imageSource = getVisionSource(file);
        ImageAnalysisOptions analysisOptions = getAnalysisOptions(ImageAnalysisFeature.TAGS);

        try (ImageAnalyzer analyzer = new ImageAnalyzer(serviceOptions, imageSource, analysisOptions);
                ImageAnalysisResult result = analyzer.analyze()) {

            if (result.getReason() != ImageAnalysisResultReason.ANALYZED) {
                ImageAnalysisErrorDetails errorDetails = ImageAnalysisErrorDetails.fromResult(result);
                printErrorDetails(errorDetails);

                throw new BaseException(Status.IMAGE_ANALYSIS_FAIL);
            }

            printObjectDetectionResult(result);
            printResultDetails(ImageAnalysisResultDetails.fromResult(result));

            return tagDetectedV40Mapper.customDetectedTags(result.getTags());
        }
    }

    private VisionSource getVisionSource(MultipartFile file) throws IOException {
        ImageSourceBuffer imageSourceBuffer = new ImageSourceBuffer();
        ImageWriter imageWriter = imageSourceBuffer.getWriter();
        imageWriter.write(ByteBuffer.wrap(file.getBytes()));
        return VisionSource.fromImageSourceBuffer(imageSourceBuffer);
    }

    private ImageAnalysisOptions getAnalysisOptions(ImageAnalysisFeature feature) {
        ImageAnalysisOptions analysisOptions = new ImageAnalysisOptions();
        analysisOptions.setFeatures(EnumSet.of(feature, ImageAnalysisFeature.TAGS));

        return analysisOptions;
    }

    private void printObjectDetectionResult(ImageAnalysisResult result) {
        System.out.println(" Image height = " + result.getImageHeight());
        System.out.println(" Image width = " + result.getImageWidth());
        System.out.println(" Model version = " + result.getModelVersion());

        if (result.getObjects() != null) {
            System.out.println(" Objects:");
            for (DetectedObject detectedObject : result.getObjects()) {
                System.out.println(detectedObject.toString());
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
