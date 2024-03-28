package com.wakeUpTogetUp.togetUp.api.mission.strategy;

import com.wakeUpTogetUp.togetUp.api.mission.model.MissionType;
import com.wakeUpTogetUp.togetUp.infra.aws.s3.model.CustomFile;
import org.springframework.web.multipart.MultipartFile;

public interface MissionImageStrategy {
    CustomFile execute(MultipartFile missionImage, MissionType type, String object);
}
