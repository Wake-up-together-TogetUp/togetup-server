package com.wakeUpTogetUp.togetUp.routines.model;

import com.wakeUpTogetUp.togetUp.users.model.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Routine {
    @Builder
    public Routine(Long missionId, String name, int estimatedTime, String icon, String color, int order) {
        this.missionId = missionId;
        this.name = name;
        this.estimatedTime = estimatedTime;
        this.icon = icon;
        this.color = color;
        this.order = order;
    }
    @Builder
    public Routine(Long missionId, String name, int estimatedTime, String icon, String color, int order, Timestamp isActivated) {
        this.missionId = missionId;
        this.name = name;
        this.estimatedTime = estimatedTime;
        this.icon = icon;
        this.color = color;
        this.order = order;
        this.isActivated = isActivated;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long missionId;
    private String name;
    private int estimatedTime;
    private String icon;
    private String color;
    private int order;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp isActivated;
}