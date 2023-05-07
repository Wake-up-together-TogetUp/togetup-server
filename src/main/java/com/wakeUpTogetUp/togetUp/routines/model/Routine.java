package com.wakeUpTogetUp.togetUp.routines.model;

import com.wakeUpTogetUp.togetUp.missions.model.Mission;
import com.wakeUpTogetUp.togetUp.users.model.User;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Table(name = "routine")
@DynamicInsert
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Routine {
    @Builder
    public Routine(User user, Mission mission, String name, int estimatedTime, String icon, String color) {
        this.user = user;
        this.mission = mission;
        this.name = name;
        this.estimatedTime = estimatedTime;
        this.icon = icon;
        this.color = color;
    }

    public void modifyProperties(Mission mission, String name, int estimatedTime, String icon, String color) {
        setMission(mission);
        setName(name);
        setEstimatedTime(estimatedTime);
        setIcon(icon);
        setColor(color);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "missionId")
    private Mission mission;
    private String name;
    private int estimatedTime;
    private String icon;
    private String color;
}