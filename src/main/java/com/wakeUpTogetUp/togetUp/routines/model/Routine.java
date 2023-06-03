package com.wakeUpTogetUp.togetUp.routines.model;

import com.wakeUpTogetUp.togetUp.alarms.model.Alarm;
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
    public Routine(Alarm alarm, String name, int estimatedTime, String icon, String color, int order) {
        this.alarm = alarm;
        this.name = name;
        this.estimatedTime = estimatedTime;
        this.icon = icon;
        this.color = color;
        this.order = order;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alarmId")
    private Alarm alarm;
    private String name;
    private int estimatedTime;
    private String icon;
    private String color;
    private int order;
}