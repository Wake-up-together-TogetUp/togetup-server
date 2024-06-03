package com.wakeUpTogetUp.togetUp.api.mission.service;

import com.wakeUpTogetUp.togetUp.api.mission.model.MissionType;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.infra.azure.vision.AzureVision32Service;
import com.wakeUpTogetUp.togetUp.infra.azure.vision.AzureVision40Service;
import com.wakeUpTogetUp.togetUp.infra.google.vision.GoogleVisionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class VisionServiceFactory {

    private final AzureVision32Service azureVision32Service;
    private final AzureVision40Service azureVision40Service;
    private final GoogleVisionService googleVisionService;

    public VisionService getVisionService(MissionType missionType) {
        switch (missionType) {
            case OBJECT_DETECTION:
                return azureVision40Service;

            case EXPRESSION_RECOGNITION:
                return googleVisionService;

            default:
                throw new BaseException(Status.INTERNAL_SERVER_ERROR);
        }
    }
}
