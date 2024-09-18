package com.wakeUpTogetUp.togetUp.api.alarm.domain;

import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import java.util.Arrays;

public enum AlarmType {
    PERSONAL,
    GROUP;

    public static AlarmType getByName(String name) {
        return Arrays.stream(values())
                .filter(value -> value.name().equals(name))
                .findFirst()
                .orElseThrow(
                        () -> new BaseException(Status.ALARM_TYPE_NOT_FOUND)
                );
    }
}
