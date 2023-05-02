package com.wakeUpTogetUp.togetUp.routines.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetRoutineRes {
    private Integer id;
    private Integer userId;
    private Integer missionId;
    private String name;
    private Integer estimatedTime;
    private String icon;
    private String color;
}
