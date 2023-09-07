package com.wakeUpTogetUp.togetUp.api.mission.objectDetection.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ObjectDetectionRes {
    private List<Prediction> predictions;

    // getters, setters 및 기타 필요한 메서드를 추가합니다.
    @Getter
    public static class Prediction {
        private int num_detections;
        private List<Double> detection_classes;
        private List<String> detection_names;
        private List<Double> detection_scores;
        private List<List<Double>> detection_boxes;
    }
}
