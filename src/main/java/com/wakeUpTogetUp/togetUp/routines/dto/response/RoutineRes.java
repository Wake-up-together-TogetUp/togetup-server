package com.wakeUpTogetUp.togetUp.routines.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoutineRes {
    private Integer id;
    private Integer userId;
    private Integer missionId;
    private String name;
    private Integer estimatedTime;
    private String icon;
    private String color;
}
