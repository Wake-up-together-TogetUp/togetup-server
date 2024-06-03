package com.wakeUpTogetUp.togetUp.api.mission.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetMissionRes {
    private int id;
    private String name;
    private String createdAt;
    private Boolean isActive;
}
