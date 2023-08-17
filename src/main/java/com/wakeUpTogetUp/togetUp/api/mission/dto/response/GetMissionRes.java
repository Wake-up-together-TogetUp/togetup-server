package com.wakeUpTogetUp.togetUp.api.mission.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetMissionRes {
    private int id;
    private String name;
    private String object;
    private String createdAt;
    private String updatedAt;
    private Boolean isActivated;
}
