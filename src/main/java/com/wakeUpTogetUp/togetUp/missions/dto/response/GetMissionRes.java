package com.wakeUpTogetUp.togetUp.missions.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetMissionRes {
    private int id;
    private String name;
    private String object;
    @Column(columnDefinition = "Tinyint")
    private boolean isActivated;
    private String createdAt;
    private String updatedAt;
}
