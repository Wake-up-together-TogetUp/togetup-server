package com.wakeUpTogetUp.togetUp.api.alarm.model;

import com.wakeUpTogetUp.togetUp.api.mission.model.MissionObject;
import com.wakeUpTogetUp.togetUp.api.room.model.Room;
import com.wakeUpTogetUp.togetUp.api.mission.model.Mission;
import com.wakeUpTogetUp.togetUp.api.users.model.User;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import java.time.DayOfWeek;

import javax.persistence.*;



@ToString
@Entity
@Table(name = "alarm")
@DynamicInsert
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private Integer id;

    private String name;

    private String icon;

    @Column(name = "snooze_interval", columnDefinition = "TINYINT")
    private Integer snoozeInterval;

    @Column(name = "snooze_cnt", columnDefinition = "TINYINT")
    private Integer snoozeCnt;

    @Column(name = "alarm_time", columnDefinition = "TIME")
    private String alarmTime;

    private Boolean monday;
    private Boolean tuesday;
    private Boolean wednesday;
    private Boolean thursday;
    private Boolean friday;
    private Boolean saturday;
    private Boolean sunday;

    private Boolean isSnoozeActivated;
    private Boolean isVibrate;
    private Boolean isActivated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", columnDefinition = "INT UNSIGNED")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_object_id")
    private MissionObject missionObject;

    @OneToOne
    @JoinColumn(name = "room_id")
    private Room room;

    // post
    @Builder
    public Alarm(String name, String icon, Integer snoozeInterval, Integer snoozeCnt, String alarmTime,
            Boolean monday, Boolean tuesday, Boolean wednesday, Boolean thursday, Boolean friday,
            Boolean saturday, Boolean sunday, Boolean isSnoozeActivated, Boolean isVibrate,
            User user, Mission mission, MissionObject missionObject) {
        this.name = name;
        this.icon = icon;
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
        this.isSnoozeActivated = isSnoozeActivated;
        this.isVibrate = isVibrate;
        this.user = user;
        this.mission = mission;
        this.missionObject = missionObject;
    }

    // patch
    @Builder
    public Alarm(String name, String icon, Integer snoozeInterval, Integer snoozeCnt, String alarmTime,
            Boolean monday, Boolean tuesday, Boolean wednesday, Boolean thursday, Boolean friday,
            Boolean saturday, Boolean sunday, Boolean isSnoozeActivated, Boolean isVibrate,
            Boolean isActivated, Mission mission, MissionObject missionObject) {
        this.name = name;
        this.icon = icon;
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
        this.isSnoozeActivated = isSnoozeActivated;
        this.isVibrate = isVibrate;
        this.isActivated = isActivated;
        this.mission = mission;
        this.missionObject = missionObject;
    }

    public void modifyProperties(String name, String icon,
            Integer snoozeInterval, Integer snoozeCnt, String alarmTime, Boolean monday,
            Boolean tuesday, Boolean wednesday, Boolean thursday, Boolean friday, Boolean saturday,
            Boolean sunday, Boolean isVibrate, Boolean isSnoozeActivated, Boolean isActivated,
            Mission mission, MissionObject missionObject) {
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
        setIsVibrate(isVibrate);
        setIsSnoozeActivated(isSnoozeActivated);
        setIsActivated(isActivated);
        setMission(mission);
        setMissionObject(missionObject);
    }

    public boolean getDayOfWeekValue(DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY:
                return getMonday();
            case TUESDAY:
                return getTuesday();
            case WEDNESDAY:
                return getWednesday();
            case THURSDAY:
                return getThursday();
            case FRIDAY:
                return getFriday();
            case SATURDAY:
                return getSaturday();
            case SUNDAY:
                return getSunday();
            default:
                throw new IllegalArgumentException("Invalid day of the week");
        }
    }
}