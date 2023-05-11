package com.wakeUpTogetUp.togetUp.mappingAlarmRoutine.model;

import com.wakeUpTogetUp.togetUp.alarms.model.Alarm;
import com.wakeUpTogetUp.togetUp.routines.model.Routine;
import com.wakeUpTogetUp.togetUp.users.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "mappingAlarmRoutine")
@Getter
@Setter
@NoArgsConstructor
public class MappingAlarmRoutine {
    @Builder
    public MappingAlarmRoutine(User user, Alarm alarm, Routine routine, int routineOrder) {
        this.user = user;
        this.alarm = alarm;
        this.routine = routine;
        this.routineOrder = routineOrder;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alarmId")
    private Alarm alarm;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routineId")
    private Routine routine;
    @Column(name = "routineOrder")
    private int routineOrder;
}
