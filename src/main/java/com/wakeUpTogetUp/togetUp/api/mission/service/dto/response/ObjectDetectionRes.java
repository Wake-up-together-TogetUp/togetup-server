package com.wakeUpTogetUp.togetUp.api.mission.service.dto.response;

import com.wakeUpTogetUp.togetUp.utils.ImageProcessing.vo.ImageProcessedResult;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ObjectDetectionRes {
    private List<Prediction> predictions;
    private ImageProcessedResult originalImageProcessedResult;

    @Getter
    public static class Prediction {
        private int num_detections;
        private List<Double> detection_classes;
        private List<String> detection_names;
        private List<Double> detection_scores;
        private List<List<Double>> detection_boxes;
    }

    public void setOriginalImageProcessedResult(ImageProcessedResult imageProcessedResult) {
        this.originalImageProcessedResult = imageProcessedResult;
    }
}
