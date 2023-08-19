package com.wakeUpTogetUp.togetUp.api.alarm.model;

import com.wakeUpTogetUp.togetUp.api.group.model.Group;
import com.wakeUpTogetUp.togetUp.api.mission.model.Mission;
import com.wakeUpTogetUp.togetUp.api.users.model.User;
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
//    @PrePersist
//    public void prePersist() {
//        this.isActivated = this.isActivated == null ? Boolean.TRUE : this.isActivated;
//        this.icon = this.icon == null ? "⏰" : this.icon;
//    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId",columnDefinition = "INT UNSIGNED")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "missionId")
    private Mission mission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomId")
    private Group group;

    private String name;

    private String icon = "⏰";

    private Boolean isVibrate;
    private Integer snoozeInterval = 5;
    private Integer snoozeCnt = 3;
    private Time alarmTime;
    private Boolean monday;
    private Boolean tuesday;
    private Boolean wednesday;
    private Boolean thursday;
    private Boolean friday;
    private Boolean saturday;
    private Boolean sunday;
    private Boolean isActivated = true;

    @Builder
    public Alarm(User user, Mission mission, String name, String icon, Boolean isVibrate, Integer snoozeInterval, Integer snoozeCnt, Time alarmTime, Boolean monday, Boolean tuesday, Boolean wednesday, Boolean thursday, Boolean friday, Boolean saturday, Boolean sunday) {
        this.user = user;
        this.mission = mission;
        this.name = name;
        this.icon = icon;
        this.isVibrate = isVibrate;
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

    public void modifyProperties(Mission mission, String name, String icon, Boolean isVibrate, Integer snoozeInterval, Integer snoozeCnt, Time alarmTime, Boolean monday, Boolean tuesday, Boolean wednesday, Boolean thursday, Boolean friday, Boolean saturday, Boolean sunday) {
        setMission(mission);
        setName(name);
        setIcon(icon);
        setIsVibrate(isVibrate);
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
}