package com.wakeUpTogetUp.togetUp.api.mission.model;

import com.wakeUpTogetUp.togetUp.api.alarm.model.Alarm;
import com.wakeUpTogetUp.togetUp.api.room.model.Room;
import com.wakeUpTogetUp.togetUp.api.users.domain.User;
import java.sql.Timestamp;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private Integer id;

    @Column(name = "alarm_name")
    private String alarmName;

    @Column(name = "mission_pic_link", columnDefinition = "Text")
    private String missionPicLink;

    @Column(name = "created_at", columnDefinition = "Timestamp")
    private Timestamp createdAt = Timestamp.from(Instant.now());

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alarm_id")
    private Alarm alarm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Builder
    public MissionLog(String alarmName, String missionPicLink, Alarm alarm, User user, Room room) {
        this.alarmName = alarmName;
        this.missionPicLink = missionPicLink;
        this.alarm = alarm;
        this.user = user;
        this.room = room;
    }
}
