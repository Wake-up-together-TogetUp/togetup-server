package com.wakeUpTogetUp.togetUp.api.mission.model;

import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;

public enum Emotion {
    JOY,
    SORROW,
    ANGER,
    SURPRISE;

    public static Emotion fromName(String object) {
        try {
            return Emotion.valueOf(object.toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new BaseException(Status.BAD_REQUEST_PARAM);
        }
    }
}
