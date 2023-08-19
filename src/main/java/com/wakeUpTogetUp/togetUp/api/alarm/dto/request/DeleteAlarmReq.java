package com.wakeUpTogetUp.togetUp.api.alarm.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class DeleteAlarmReq {
    @NotNull(message = "userId는 null일 수 없습니다.")
    private Integer userId;
}
