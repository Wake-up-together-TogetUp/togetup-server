package com.wakeUpTogetUp.togetUp.routines.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
public class DeleteRoutineReq {
    @NotNull(message = "userId는 null일 수 없습니다.")
    private Integer userId;
}
