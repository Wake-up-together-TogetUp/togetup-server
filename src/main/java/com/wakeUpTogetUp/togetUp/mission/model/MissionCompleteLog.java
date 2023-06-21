package com.wakeUpTogetUp.togetUp.mission.model;

import com.wakeUpTogetUp.togetUp.alarm.model.Alarm;
import com.wakeUpTogetUp.togetUp.users.model.User;
import com.wakeUpTogetUp.togetUp.utils.TimestampFormatter;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "missionCompleteLog")
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert
public class MissionCompleteLog {
    @PrePersist
    public void prePersist() {
        this.createdAt = TimestampFormatter.format(new Timestamp(System.currentTimeMillis()));
        this.isActivated = this.isActivated == null ? Boolean.TRUE : this.isActivated;
    }

    @Builder
    public MissionCompleteLog(String type, String title, String picLink, User user, Alarm alarm) {
        this.type = type;
        this.title = title;
        this.picLink = picLink;
        this.user = user;
        this.alarm = alarm;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    // TODO : ENUM 처리하기
    private String type;
    private String title;
    @Column(columnDefinition = "Text")
    private String picLink;
    @Column(columnDefinition = "Timestamp")
    private String createdAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alarmId")
    private Alarm alarm;
    private Boolean isActivated;
}
