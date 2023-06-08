package com.wakeUpTogetUp.togetUp.exception;

import com.wakeUpTogetUp.togetUp.common.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

// unchecked exception을 위해 RuntimeException을 상속받음
@Getter
@Setter
@AllArgsConstructor
public class BaseException extends RuntimeException {
    private Status status;
}
