package com.wakeUpTogetUp.togetUp.api.mission.service;

import com.wakeUpTogetUp.togetUp.api.mission.domain.CustomAnalysisEntity;
import com.wakeUpTogetUp.togetUp.utils.file.FileUtil;
import com.wakeUpTogetUp.togetUp.utils.image.ImageDrawer;
import com.wakeUpTogetUp.togetUp.infra.aws.s3.model.CustomFile;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MissionImageService {

    private final ImageDrawer imageDrawer;

    public CustomFile processResultImage(
            MultipartFile file,
            List<CustomAnalysisEntity> entities,
            String object
    ) {
        if (entities.isEmpty()) {
            return CustomFile.fromProcessedFile(file, FileUtil.getBytes(file));
        }

        byte[] drawnImageBytes = imageDrawer.drawResultOnImage(file, entities, object);
        return CustomFile.fromProcessedFile(file, drawnImageBytes);
    }
}