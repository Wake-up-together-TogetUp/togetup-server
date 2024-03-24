package com.wakeUpTogetUp.togetUp.api.mission.service;

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
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.EnumSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AzureVision40Service {
    @Value("${azure.computer-vision.endpoint}")
    private String endpoint;

    @Value("${azure.computer-vision.key}")
    private String key;

    public List<DetectedObject> detectObjectByVer40(MultipartFile file) throws Exception {
        VisionServiceOptions serviceOptions = new VisionServiceOptions(new URL(endpoint), key);
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

            return result.getObjects();
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
        analysisOptions.setFeatures(EnumSet.of(feature));

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
