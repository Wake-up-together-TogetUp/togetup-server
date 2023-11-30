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
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;

@ToString
@Entity
@Table(name = "alarm")
@DynamicInsert
@Data
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

    @Column(columnDefinition = "TINYINT")
    private Integer snoozeInterval;

    @Column(columnDefinition = "TINYINT")
    private Integer snoozeCnt;

    @Column(columnDefinition = "TIME")
    private LocalTime alarmTime;

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

    public void modifyProperties(String name, String icon,
                                 Integer snoozeInterval, Integer snoozeCnt, LocalTime alarmTime, Boolean monday,
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

    public AlarmType determineAlarmType() {
        return Objects.isNull(room) ? AlarmType.PERSONAL : AlarmType.GROUP;
    }
}