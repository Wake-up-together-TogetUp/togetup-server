package com.wakeUpTogetUp.togetUp.api.mission.service.dto.response;

import com.wakeUpTogetUp.togetUp.utils.ImageProcessing.vo.ImageProcessedResult;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FaceRecognitionRes {
    private Info info;
    private List<Face> faces;
    private ImageProcessedResult originalImageProcessedResult;

    public void setOriginalImageProcessedResult(ImageProcessedResult imageProcessedResult){
        this.originalImageProcessedResult = imageProcessedResult;
    }

    @Getter
    public static class Info {
        private Size size;
        private int faceCount;

        @Getter
        @AllArgsConstructor
        public static class Size {
            private int width;
            private int height;
        }
    }
    @Getter
    public static class Face {
        private Roi roi;
        private Landmark landmark;
        private Attribute<String> gender;
        private Attribute<String> age;
        private Attribute<EmotionValue> emotion;
        private Attribute<PoseValue> pose;

        @Getter
        public static class Roi {
            private int x;
            private int y;
            private int width;
            private int height;
        }

        @Getter
        public static class Landmark {
            private Point leftEye;
            private Point rightEye;
            private Point nose;
            private Point leftMouth;
            private Point rightMouth;

            @Getter
            public static class Point {
                private int x;
                private int y;
            }
        }

        @Getter
        public static class Attribute<T> {
            private T value;
            private double confidence;
        }

        public enum EmotionValue {
            angry,
            disgust,
            fear,
            laugh,
            neutral,
            sad,
            suprise,
            smile,
            talking
        }

        public enum PoseValue {
            part_face,
            false_face,
            sunglasses,
            frontal_face,
            left_face,
            right_face,
            rotate_face,
        }
    }
}
