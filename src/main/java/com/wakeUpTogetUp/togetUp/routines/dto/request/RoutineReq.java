package com.wakeUpTogetUp.togetUp.routines.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Null;

@Getter
@Setter
@NoArgsConstructor
public class RoutineReq {
    public RoutineReq(Integer userId, Integer missionId, String name, Integer estimatedTime, String icon, String color) {
        this.userId = userId;
        this.missionId = missionId;
        this.name = name;
        this.estimatedTime = estimatedTime;
        this.icon = icon;
        this.color = color;
    }
    @Null
    private Integer id;
    private Integer userId;
    private Integer missionId;
    private String name;
    private Integer estimatedTime;
    private String icon;
    private String color;
}
