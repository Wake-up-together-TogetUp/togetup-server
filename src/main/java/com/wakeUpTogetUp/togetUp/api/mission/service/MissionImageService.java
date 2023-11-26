package com.wakeUpTogetUp.togetUp.api.mission.service;

import com.azure.ai.vision.imageanalysis.DetectedObject;
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

    public ImageProcessResult processResultImage(MultipartFile file, List<DetectedObject> detectedObjects,
                                                 String object)
            throws Exception {
        // TODO : 파일 품질 손상 고치기
        TiffImageMetadata metadata = imageProcessor.getImageMetadata(file);
        byte[] drawnImageBytes = imageProcessor.drawODResultOnImage(file, detectedObjects, object);
//        byte[] compressedImageBytes = imageProcessor.compress(drawnImageBytes, 0.8f);
        byte[] orientedImageBytes = imageProcessor.orientImage(drawnImageBytes, metadata);

        return new ImageProcessResult(orientedImageBytes);
    }
}
