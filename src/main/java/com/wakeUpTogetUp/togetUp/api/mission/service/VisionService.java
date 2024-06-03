package com.wakeUpTogetUp.togetUp.api.mission.service;

import com.wakeUpTogetUp.togetUp.api.mission.domain.VisionAnalysisResult;
import org.springframework.web.multipart.MultipartFile;

public interface VisionService {

    VisionAnalysisResult getResult(MultipartFile file, String target);
}
