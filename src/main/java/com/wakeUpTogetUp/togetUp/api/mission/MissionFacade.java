package com.wakeUpTogetUp.togetUp.api.mission;

import com.wakeUpTogetUp.togetUp.api.mission.dto.response.MissionPerfomRes;
import com.wakeUpTogetUp.togetUp.api.mission.strategy.MissionImageStrategyFactory;
import com.wakeUpTogetUp.togetUp.api.mission.model.MissionType;
import com.wakeUpTogetUp.togetUp.infra.aws.s3.FileService;
import com.wakeUpTogetUp.togetUp.infra.aws.s3.model.CustomFile;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MissionFacade {

    private final MissionImageStrategyFactory missionImageStrategyFactory;
    private final MissionProvider missionProvider;
    private final FileService fileService;

    public MissionPerfomRes performMission(MultipartFile missionImage, String missionName, String object) {
        MissionType type = MissionType.getByName(missionName);
        missionProvider.validateMissionObject(type, object);

        CustomFile file = missionImageStrategyFactory
                .getStrategy(type)
                .execute(missionImage, type, object);
        String imageUrl = fileService.uploadFile(file, "mission");

        return new MissionPerfomRes(imageUrl);
    }
}
