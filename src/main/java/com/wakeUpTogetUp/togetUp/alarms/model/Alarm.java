package com.wakeUpTogetUp.togetUp.alarms.model;

import com.wakeUpTogetUp.togetUp.users.model.User;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Table(name = "alarm")
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert          // insert 시 값이 null인 필드 제외
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Builder
    private Alarm(User user, String name, String icon, String sound, Integer volume, Boolean isVibrate, Boolean isRoutineOn, Integer snoozeInterval, Integer snoozeCnt, Integer startHour, Integer startMinute, Boolean monday, Boolean tuesday, Boolean wednesday, Boolean thursday, Boolean friday, Boolean saturday, Boolean sunday) {
        this.user = user;
        this.name = name;
        this.icon = icon;
        this.sound = sound;
        this.volume = volume;
        this.isVibrate = isVibrate;
        this.isRoutineOn = isRoutineOn;
        this.snoozeInterval = snoozeInterval;
        this.snoozeCnt = snoozeCnt;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
    }

    // TODO : 접근제어자를 public으로 두는게 맞나?
    public void modifyProperties(String name, String icon, String sound, Integer volume, Boolean isVibrate, Boolean isRoutineOn, Integer snoozeInterval, Integer snoozeCnt, Integer startHour, Integer startMinute, Boolean monday, Boolean tuesday, Boolean wednesday, Boolean thursday, Boolean friday, Boolean saturday, Boolean sunday) {
        this.name = name;
        this.icon = icon;
        this.sound = sound;
        this.volume = volume;
        this.isVibrate = isVibrate;
        this.isRoutineOn = isRoutineOn;
        this.snoozeInterval = snoozeInterval;
        this.snoozeCnt = snoozeCnt;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;
    private String name;
    private String icon;
    private String sound;
    private Integer volume;
    private Boolean isVibrate;
    private Boolean isRoutineOn;
    private Integer snoozeInterval;
    private Integer snoozeCnt;
    private Integer startHour;
    private Integer startMinute;
    private Boolean monday;
    private Boolean tuesday;
    private Boolean wednesday;
    private Boolean thursday;
    private Boolean friday;
    private Boolean saturday;
    private Boolean sunday;
    private Boolean isActivated;
}