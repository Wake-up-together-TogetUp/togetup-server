package com.wakeUpTogetUp.togetUp.api.mission.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetMissionObjectRes {
    private Integer id;
    private String name;
    private String kr;
    private String icon;
}
