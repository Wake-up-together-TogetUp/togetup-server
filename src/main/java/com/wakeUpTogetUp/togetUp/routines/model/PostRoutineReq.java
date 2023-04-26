package com.wakeUpTogetUp.togetUp.routines.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostRoutineReq {
    private int missionId;
    private String name;
    private int estimatedTime;
    private String icon;
    private String color;
}
