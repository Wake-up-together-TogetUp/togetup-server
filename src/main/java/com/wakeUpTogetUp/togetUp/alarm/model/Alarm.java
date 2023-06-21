package com.wakeUpTogetUp.togetUp.alarm.model;

import com.wakeUpTogetUp.togetUp.mission.model.Mission;
import com.wakeUpTogetUp.togetUp.users.model.User;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "alarm")
@DynamicInsert          // insert 시 값이 null인 필드 제외
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Alarm {
    @PrePersist
    public void prePersist() {
        this.isActivated = this.isActivated == null ? Boolean.TRUE : this.isActivated;
        this.icon = this.icon == null ? "⏰" : this.icon;
    }
    @Builder
    public Alarm(User user, Mission mission, String name, String icon, String sound, Boolean isVibrate, Boolean isRoutineOn, Integer snoozeInterval, Integer snoozeCnt, Time alarmTime, Boolean monday, Boolean tuesday, Boolean wednesday, Boolean thursday, Boolean friday, Boolean saturday, Boolean sunday) {
        this.user = user;
        this.mission = mission;
        this.name = name;
        this.icon = icon;
        this.sound = sound;
        this.isVibrate = isVibrate;
        this.isRoutineOn = isRoutineOn;
        this.snoozeInterval = snoozeInterval;
        this.snoozeCnt = snoozeCnt;
        this.alarmTime = alarmTime;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
    }

    public void modifyProperties(Mission mission, String name, String icon, String sound, Boolean isVibrate, Boolean isRoutineOn, Integer snoozeInterval, Integer snoozeCnt, Time alarmTime, Boolean monday, Boolean tuesday, Boolean wednesday, Boolean thursday, Boolean friday, Boolean saturday, Boolean sunday) {
        setMission(mission);
        setName(name);
        setIcon(icon);
        setSound(sound);
        setIsVibrate(isVibrate);
        setIsRoutineOn(isRoutineOn);
        setSnoozeInterval(snoozeInterval);
        setSnoozeCnt(snoozeCnt);
        setAlarmTime(alarmTime);
        setMonday(monday);
        setTuesday(tuesday);
        setWednesday(wednesday);
        setThursday(thursday);
        setFriday(friday);
        setSaturday(saturday);
        setSunday(sunday);
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "missionId")
    private Mission mission;
    private String name;
    private String icon;
    private String sound;
    private Boolean isVibrate;
    private Boolean isRoutineOn;
    private Integer snoozeInterval;
    private Integer snoozeCnt;
    private Time alarmTime;
    private Boolean monday;
    private Boolean tuesday;
    private Boolean wednesday;
    private Boolean thursday;
    private Boolean friday;
    private Boolean saturday;
    private Boolean sunday;
    private Boolean isActivated;
}