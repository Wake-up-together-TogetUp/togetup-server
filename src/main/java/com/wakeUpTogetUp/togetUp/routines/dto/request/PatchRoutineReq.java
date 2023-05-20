package com.wakeUpTogetUp.togetUp.routines.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Getter
@Setter
@NoArgsConstructor
public class PatchRoutineReq {
    @NotNull(message = "userId는 null일 수 없습니다.")
    private Integer userId;
    @NotNull(message = "missionId는 null일 수 없습니다.")
    private Integer missionId;
    @NotBlank(message = "routine의 name은 공백일 수 없습니다.")
    private String name;
    @NotNull(message = "estimatedTime은 null일 수 없습니다.")
    private Integer estimatedTime;
    private String icon;
    private String color;
}
