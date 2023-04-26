package com.wakeUpTogetUp.togetUp.routines.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetRoutineRes {
    private int id;
    private int userId;
    private int missionId;
    private String name;
    private int estimatedTime;
    private String icon;
    private String color;
}
