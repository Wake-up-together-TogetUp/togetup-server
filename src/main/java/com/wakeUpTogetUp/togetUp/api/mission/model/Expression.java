package com.wakeUpTogetUp.togetUp.api.mission.model;

import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;

public enum Expression {
    JOY,
    SORROW,
    ANGER,
    SURPRISE;

    public static Expression fromName(String name) {
        try {
            return Expression.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new BaseException("해당하는 감정 열거형 값이 없습니다.", exception, Status.BAD_REQUEST_PARAM);
        }
    }
}
