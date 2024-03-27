package com.wakeUpTogetUp.togetUp.api.mission.domain;

import org.springframework.web.multipart.MultipartFile;

public interface VisionService {

    VisionAnalysisResult getResult(MultipartFile file, String target);
}
