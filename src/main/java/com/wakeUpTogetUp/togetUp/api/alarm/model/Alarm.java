package com.wakeUpTogetUp.togetUp.api.alarm.model;

import com.wakeUpTogetUp.togetUp.api.mission.model.Mission;
import com.wakeUpTogetUp.togetUp.api.mission.model.MissionObject;
import com.wakeUpTogetUp.togetUp.api.room.model.Room;
import com.wakeUpTogetUp.togetUp.api.users.model.User;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "alarm")
@SQLDelete(sql = "UPDATE alarm SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
@DynamicInsert
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private Integer id;

    private String name;
    private String icon;
    @Column(columnDefinition = "TIME")
    private LocalTime alarmTime;
    private Boolean monday;
    private Boolean tuesday;
    private Boolean wednesday;
    private Boolean thursday;
    private Boolean friday;
    private Boolean saturday;
    private Boolean sunday;
    private Boolean isVibrate;
    private Boolean isActivated;
    private Boolean isDeleted;

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

    @Builder
    private Alarm(String name, String icon, LocalTime alarmTime, Boolean monday, Boolean tuesday,
            Boolean wednesday, Boolean thursday, Boolean friday, Boolean saturday, Boolean sunday,
            Boolean isVibrate, User user, Mission mission, MissionObject missionObject) {
        this.name = name;
        this.icon = icon;
        this.alarmTime = alarmTime;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
        this.isVibrate = isVibrate;
        this.user = user;
        this.mission = mission;
        this.missionObject = missionObject;
    }

    public static Alarm create(String name, String icon, LocalTime alarmTime, Boolean monday,
            Boolean tuesday, Boolean wednesday, Boolean thursday, Boolean friday, Boolean saturday,
            Boolean sunday, Boolean isVibrate, User user, Mission mission,
            MissionObject missionObject) {
        return Alarm.builder()
                .name(name)
                .icon(icon)
                .alarmTime(alarmTime)
                .monday(monday)
                .tuesday(tuesday)
                .wednesday(wednesday)
                .thursday(thursday)
                .friday(friday)
                .saturday(saturday)
                .sunday(sunday)
                .isVibrate(isVibrate)
                .user(user)
                .mission(mission)
                .missionObject(missionObject)
                .build();
    }

    public void modifyProperties(String name, String icon, LocalTime alarmTime, Boolean monday,
            Boolean tuesday, Boolean wednesday, Boolean thursday, Boolean friday, Boolean saturday,
            Boolean sunday, Boolean isVibrate, Boolean isActivated, Mission mission,
            MissionObject missionObject) {
        this.name = name;
        this.icon = icon;
        this.isVibrate = isVibrate;
        this.alarmTime = alarmTime;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
        this.isActivated = isActivated;
        this.isDeleted = false;
        this.mission = mission;
        this.missionObject = missionObject;
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

    public AlarmType determineAlarmType() {
        return isRoomAlarm() ? AlarmType.GROUP : AlarmType.PERSONAL;
    }

    public boolean isRoomAlarm() {
        return Objects.nonNull(room);
    }
}