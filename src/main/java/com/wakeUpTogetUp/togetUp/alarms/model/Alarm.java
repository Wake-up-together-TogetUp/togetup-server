package com.wakeUpTogetUp.togetUp.alarms.model;

import com.wakeUpTogetUp.togetUp.users.model.UserEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "alarm")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert          // insert 시 값이 null인 필드 제외
public class Alarm {
    // alarm 생성 시
    @Builder
    public Alarm(UserEntity user, String name, String icon, String sound, int volume, Boolean isEnabled, Boolean isVibrate, Boolean isRoutineOn, int snoozeInterval, int snoozeCnt, String startHour, String startMinute, String endHour, String endMinute, Boolean monday, Boolean tuesday, Boolean wednesday, Boolean thursday, Boolean friday, Boolean saturday, Boolean sunday) {
        this.user = user;
        this.name = name;
        this.icon = icon;
        this.sound = sound;
        this.volume = volume;
        this.isEnabled = isEnabled;
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
    // isActivate 추가
    @Builder
    public Alarm(UserEntity user, String name, String icon, String sound, int volume, Boolean isEnabled, Boolean isVibrate, Boolean isRoutineOn, int snoozeInterval, int snoozeCnt, String startHour, String startMinute, String endHour, String endMinute, Boolean monday, Boolean tuesday, Boolean wednesday, Boolean thursday, Boolean friday, Boolean saturday, Boolean sunday, Timestamp isActivated) {
        this.user = user;
        this.name = name;
        this.icon = icon;
        this.sound = sound;
        this.volume = volume;
        this.isEnabled = isEnabled;
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
        this.isActivated = isActivated;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity user;
    private String name;
    private String icon;
    private String sound;
    private int volume;
    @Column(name = "isEnabled")
    private Boolean isEnabled;
    @Column(name = "isVibrate")
    private Boolean isVibrate;
    @Column(name = "isRoutineOn")
    private Boolean isRoutineOn;
    @Column(name = "snoozeInterval")
    private int snoozeInterval;
    private int snoozeCnt;
    private String startHour;
    private String startMinute;
    private Boolean monday;
    private Boolean tuesday;
    private Boolean wednesday;
    private Boolean thursday;
    private Boolean friday;
    private Boolean saturday;
    private Boolean sunday;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp isActivated;
}