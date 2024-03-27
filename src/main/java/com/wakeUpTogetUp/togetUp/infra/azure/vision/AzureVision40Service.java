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
import com.wakeUpTogetUp.togetUp.api.mission.domain.VisionAnalysisResult;
import com.wakeUpTogetUp.togetUp.api.mission.domain.VisionService;
import com.wakeUpTogetUp.togetUp.infra.azure.vision.mapper.ObjectDetectedV40Mapper;
import com.wakeUpTogetUp.togetUp.infra.azure.vision.mapper.TagDetectedV40Mapper;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.utils.image.ImageProcessor;
import java.io.IOException;
import java.nio.ByteBuffer;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AzureVision40Service implements VisionService {

    private final int IMAGE_SIZE_LIMIT = 10;

    private final VisionServiceOptions serviceOptions;
    private final ImageAnalysisOptions analysisOptions;
    private final ObjectDetectedV40Mapper objectDetectedV40Mapper;
    private final TagDetectedV40Mapper tagDetectedV40Mapper;

    public VisionAnalysisResult getResult(MultipartFile file, String target) {
        byte[] data = ImageProcessor.compressUntil(file, IMAGE_SIZE_LIMIT);

        try (ImageAnalyzer analyzer = new ImageAnalyzer(serviceOptions, getVisionSource(data),
                analysisOptions);
                ImageAnalysisResult result = analyzer.analyze()) {

            catchNotAnalyzedError(result);
            printDetectionResult(result);
            printResultDetails(ImageAnalysisResultDetails.fromResult(result));

            return ObjectDetectionResult.builder()
                    .target(target)
                    .objects(objectDetectedV40Mapper.toCustomDetectedObjects(result.getObjects()))
                    .tags(tagDetectedV40Mapper.customDetectedTags(result.getTags()))
                    .build();
        } catch (IOException e) {
            throw new BaseException(Status.INVALID_IMAGE);
        } catch (Exception e) {
            throw new BaseException(Status.INTERNAL_SERVER_ERROR);
        }
    }

    private VisionSource getVisionSource(byte[] data) {
        ImageSourceBuffer imageSourceBuffer = new ImageSourceBuffer();

        try (ImageWriter imageWriter = imageSourceBuffer.getWriter()) {
            imageWriter.write(ByteBuffer.wrap(data));
        }

        return VisionSource.fromImageSourceBuffer(imageSourceBuffer);
    }

    private void catchNotAnalyzedError(ImageAnalysisResult result) {
        if (isNotAnalyzed(result)) {
            ImageAnalysisErrorDetails errorDetails = ImageAnalysisErrorDetails.fromResult(result);
            printErrorDetails(errorDetails);

            throw new BaseException(Status.IMAGE_ANALYSIS_FAIL);
        }
    }

    private boolean isNotAnalyzed(ImageAnalysisResult result) {
        return result.getReason() != ImageAnalysisResultReason.ANALYZED;
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
