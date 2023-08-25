package com.wakeUpTogetUp.togetUp.api.alarm.model;

import com.wakeUpTogetUp.togetUp.api.room.model.Room;
import com.wakeUpTogetUp.togetUp.api.mission.model.Mission;
import com.wakeUpTogetUp.togetUp.api.users.model.User;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "alarm")
@DynamicInsert
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", columnDefinition = "INT UNSIGNED")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    private String name;
    private String icon;
    @Column(name = "is_vibrate")
    private Boolean isVibrate;

    @Column(name = "snooze_interval",columnDefinition = "TINYINT")
    private Integer snoozeInterval;

    @Column(name = "snooze_cnt" ,columnDefinition = "TINYINT")
    private Integer snoozeCnt;

    @Column(name = "alarm_time" ,columnDefinition = "TIME")
    private String alarmTime;

    private Boolean monday;
    private Boolean tuesday;
    private Boolean wednesday;
    private Boolean thursday;
    private Boolean friday;
    private Boolean saturday;
    private Boolean sunday;

    @Column(name = "is_activated")
    private Boolean isActivated;

    @Builder
    public Alarm(User user, Mission mission, String name, String icon, Boolean isVibrate,
            Integer snoozeInterval, Integer snoozeCnt, String alarmTime, Boolean monday,
            Boolean tuesday, Boolean wednesday, Boolean thursday, Boolean friday, Boolean saturday,
            Boolean sunday) {
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

    public void modifyProperties(Mission mission, String name, String icon, Boolean isVibrate,
            Integer snoozeInterval, Integer snoozeCnt, String alarmTime, Boolean monday,
            Boolean tuesday, Boolean wednesday, Boolean thursday, Boolean friday, Boolean saturday,
            Boolean sunday, Boolean isActivated) {
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
        setIsActivated(isActivated);
    }
}