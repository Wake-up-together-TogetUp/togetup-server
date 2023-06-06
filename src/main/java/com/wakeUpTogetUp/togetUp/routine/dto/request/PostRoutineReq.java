package com.wakeUpTogetUp.togetUp.routine.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class PostRoutineReq {
    @NotBlank(message = "routine의 name은 공백일 수 없습니다.")
    private String name;
    @NotNull(message = "estimatedTime은 null일 수 없습니다.")
    private Integer estimatedTime;
    private String icon;
//    private String color;
    private Integer routineOrder;
}
