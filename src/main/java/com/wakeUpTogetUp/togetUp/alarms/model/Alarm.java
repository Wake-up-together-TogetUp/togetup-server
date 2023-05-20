package com.wakeUpTogetUp.togetUp.alarms.model;

import com.wakeUpTogetUp.togetUp.missions.model.Mission;
import com.wakeUpTogetUp.togetUp.users.model.User;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Table(name = "alarm")
@DynamicInsert          // insert 시 값이 null인 필드 제외
@Getter
@Setter
@NoArgsConstructor
public class Alarm {
    // TODO : 접근제어자를 public으로 두는게 맞나?
    public void modifyProperties(Mission mission, String name, String icon, String sound, Integer volume, Boolean isVibrate, Boolean isRoutineOn, Integer snoozeInterval, Integer snoozeCnt, Integer startHour, Integer startMinute, Boolean monday, Boolean tuesday, Boolean wednesday, Boolean thursday, Boolean friday, Boolean saturday, Boolean sunday) {
        setMission(mission);
        setName(name);
        setIcon(icon);
        setSound(sound);
        setVolume(volume);
        setIsVibrate(isVibrate);
        setIsRoutineOn(isRoutineOn);
        setSnoozeInterval(snoozeInterval);
        setSnoozeCnt(snoozeCnt);
        setStartHour(startHour);
        setStartMinute(startMinute);
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