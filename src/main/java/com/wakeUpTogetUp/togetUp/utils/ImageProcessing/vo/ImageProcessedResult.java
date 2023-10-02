package com.wakeUpTogetUp.togetUp.utils.ImageProcessing.vo;

import com.wakeUpTogetUp.togetUp.api.mission.service.dto.response.FaceRecognitionRes.Info.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ImageProcessedResult {
    byte[] result;
    Size size;
}