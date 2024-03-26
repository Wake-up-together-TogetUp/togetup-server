package com.wakeUpTogetUp.togetUp.infra.azure.vision.mapper;

import com.azure.ai.vision.imageanalysis.ContentTag;
import com.wakeUpTogetUp.togetUp.api.mission.domain.CustomDetectedTag;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class TagDetectedV40Mapper {

    public List<CustomDetectedTag> customDetectedTags(List<ContentTag> tags) {
        return tags.stream()
                .map(this::toCustomDetectedTag)
                .collect(Collectors.toList());
    }

    private CustomDetectedTag toCustomDetectedTag(ContentTag tag) {
        return CustomDetectedTag.builder()
                .tagName(tag.getName())
                .confidence(tag.getConfidence())
                .build();
    }
}
