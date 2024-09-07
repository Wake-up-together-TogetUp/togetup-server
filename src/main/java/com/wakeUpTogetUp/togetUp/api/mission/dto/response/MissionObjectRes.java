package com.wakeUpTogetUp.togetUp.api.mission.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MissionObjectRes {
    private int id;
    private String name;
    private String kr;
    private String icon;
}
