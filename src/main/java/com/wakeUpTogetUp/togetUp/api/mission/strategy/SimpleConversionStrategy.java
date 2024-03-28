package com.wakeUpTogetUp.togetUp.api.mission.strategy;

import com.wakeUpTogetUp.togetUp.api.mission.model.MissionType;
import com.wakeUpTogetUp.togetUp.infra.aws.s3.model.CustomFile;
import com.wakeUpTogetUp.togetUp.utils.file.FileUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class SimpleConversionStrategy implements MissionImageStrategy {

    @Override
    public CustomFile execute(MultipartFile missionImage, MissionType type, String object) {
        return CustomFile.fromProcessedFile(missionImage, FileUtil.getBytes(missionImage));
    }
}
