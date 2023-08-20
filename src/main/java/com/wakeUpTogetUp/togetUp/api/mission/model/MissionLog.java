package com.wakeUpTogetUp.togetUp.api.mission.model;

import com.wakeUpTogetUp.togetUp.api.room.model.Room;
import com.wakeUpTogetUp.togetUp.api.users.model.User;
import com.wakeUpTogetUp.togetUp.utils.TimestampFormatter;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "mission_log")
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert
public class MissionLog {
//    @PrePersist
//    public void prePersist() {
//        this.createdAt = TimestampFormatter.format(new Timestamp(System.currentTimeMillis()));
//        this.isActivated = this.isActivated == null ? Boolean.TRUE : this.isActivated;
//    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private Integer id;

    private String alarmName;

    @Column(columnDefinition = "Text")
    private String missionPicLink;

    @Column(columnDefinition = "Timestamp")
    private String createdAt = TimestampFormatter.format(new Timestamp(System.currentTimeMillis()));

    private Boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomId")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "missionId")
    private Mission mission;

    @Builder
    public MissionLog(String alarmName, String missionPicLink, User user, Room room,
            Mission mission) {
        this.alarmName = alarmName;
        this.missionPicLink = missionPicLink;
        this.user = user;
        this.room = room;
        this.mission = mission;
    }
}
