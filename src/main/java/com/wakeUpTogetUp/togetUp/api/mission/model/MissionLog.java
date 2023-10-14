package com.wakeUpTogetUp.togetUp.api.mission.model;

import com.wakeUpTogetUp.togetUp.api.room.model.Room;
import com.wakeUpTogetUp.togetUp.api.users.model.User;
import com.wakeUpTogetUp.togetUp.utils.TimeFormatter;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@DynamicInsert
@Table(name = "mission_log")
@SQLDelete(sql = "UPDATE mission_log SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
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

    @Column(name = "alarm_name")
    private String alarmName;

    @Column(name = "mission_pic_link",columnDefinition = "Text")
    private String missionPicLink;

    @Column(name = "created_at",columnDefinition = "Timestamp")
    private String createdAt = TimeFormatter.timestampFormat(new Timestamp(System.currentTimeMillis()));

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Builder
    public MissionLog(String alarmName, String missionPicLink, User user, Room room) {
        this.alarmName = alarmName;
        this.missionPicLink = missionPicLink;
        this.user = user;
        this.room = room;
    }
}
