package com.wakeUpTogetUp.togetUp.infra.azure.vision.mapper;

import com.microsoft.azure.cognitiveservices.vision.computervision.models.ImageTag;
import com.wakeUpTogetUp.togetUp.api.mission.domain.CustomDetectedTag;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class TagDetectedV32Mapper {

    public List<CustomDetectedTag> toCustomDetectedTags(List<ImageTag> tags) {
        return tags.stream()
                .map(this::toCustomDetectedTag)
                .collect(Collectors.toList());
    }

    private CustomDetectedTag toCustomDetectedTag(ImageTag tag) {
        return CustomDetectedTag.builder()
                .name(tag.name())
                .confidence(tag.confidence())
                .build();
    }
}
