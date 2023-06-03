package com.wakeUpTogetUp.togetUp.routines.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoutineRes {
    private int id;
    private int alarmId;
    private String name;
    private int estimatedTime;
    private String icon;
    private String color;
    private int routineOrder;
}
