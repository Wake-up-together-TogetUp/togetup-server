package com.wakeUpTogetUp.togetUp.api.mission.strategy;

import com.wakeUpTogetUp.togetUp.api.mission.service.MissionImageService;
import com.wakeUpTogetUp.togetUp.api.mission.service.MissionProvider;
import com.wakeUpTogetUp.togetUp.api.mission.service.MissionService;
import com.wakeUpTogetUp.togetUp.api.mission.domain.CustomAnalysisEntity;
import com.wakeUpTogetUp.togetUp.api.mission.model.MissionType;
import com.wakeUpTogetUp.togetUp.infra.aws.s3.model.CustomFile;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AnalysisConversionStrategy implements MissionImageStrategy {

    private final MissionService missionService;
    private final MissionImageService missionImageService;
    private final MissionProvider missionProvider;

    @Override
    public CustomFile execute(MultipartFile missionImage, MissionType type, String object) {
        missionProvider.validateMissionObject(type, object);

        List<CustomAnalysisEntity> detectedObjects = missionService.getMissionResult(type, object, missionImage);
        return missionImageService.processResultImage(missionImage, detectedObjects, object);
    }
}
