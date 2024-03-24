package com.wakeUpTogetUp.togetUp.api.mission.service;

import com.google.cloud.vision.v1.FaceAnnotation;
import com.wakeUpTogetUp.togetUp.api.mission.model.CustomDetectedObject;
import com.wakeUpTogetUp.togetUp.utils.ImageProcessing.ImageProcessor;
import com.wakeUpTogetUp.togetUp.utils.ImageProcessing.vo.ImageProcessResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MissionImageService {
    private final ImageProcessor imageProcessor;

    public ImageProcessResult processODResultImage(
            MultipartFile file, List<CustomDetectedObject> detectedObjects) throws Exception {
        // TODO : 파일 품질 손상 고치기
        TiffImageMetadata metadata = imageProcessor.getImageMetadata(file);
        byte[] drawnImageBytes = imageProcessor.drawODResultOnImage(file, detectedObjects);
        byte[] orientedImageBytes = imageProcessor.orientImage(drawnImageBytes, metadata);

        return new ImageProcessResult(orientedImageBytes);
    }

    public ImageProcessResult processFRResultImage(
            MultipartFile file, List<FaceAnnotation> faceAnnotations, String object) throws Exception {
        TiffImageMetadata metadata = imageProcessor.getImageMetadata(file);
        byte[] drawnImageBytes = imageProcessor.drawFRResultOnImage(file, faceAnnotations, object);
        byte[] orientedImageBytes = imageProcessor.orientImage(drawnImageBytes, metadata);

        return new ImageProcessResult(orientedImageBytes);
    }
}