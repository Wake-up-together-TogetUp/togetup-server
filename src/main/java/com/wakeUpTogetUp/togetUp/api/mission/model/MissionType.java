package com.wakeUpTogetUp.togetUp.api.mission.model;

import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import java.util.Arrays;
import lombok.Getter;

public enum MissionType {

    DIRECT_REGISTRATION("direct-registration"),
    OBJECT_DETECTION("object-detection"),
    EXPRESSION_RECOGNITION("face-recognition");

    @Getter
    private final String name;

    MissionType(String name) {
        this.name = name;
    }

    public static MissionType getByName(String name) {
        return Arrays.stream(MissionType.values())
                .filter(type -> type.getName().equals(name))
                .findAny()
                .orElseThrow(() -> new BaseException(Status.MISSION_NOT_FOUND));
    }
}
