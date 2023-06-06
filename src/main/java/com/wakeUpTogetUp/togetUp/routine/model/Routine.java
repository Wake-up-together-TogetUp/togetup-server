package com.wakeUpTogetUp.togetUp.routine.model;

import com.wakeUpTogetUp.togetUp.alarms.model.Alarm;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(name = "routine")
//@DynamicInsert
//@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Routine {
    @PrePersist
    public void prePersist() {
        this.color = this.color == null ? "#E0EBFF" : this.color;
    }

    @Builder
    public Routine(Alarm alarm, String name, int estimatedTime, String icon, int routineOrder) {
        this.alarm = alarm;
        this.name = name;
        this.estimatedTime = estimatedTime;
        this.icon = icon;
        this.routineOrder = routineOrder;
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
    @Column(name = "color")
    @ColumnDefault("#E0EBFF")
    private String color;
    @Column(columnDefinition = "Tinyint")
    private int routineOrder;
}