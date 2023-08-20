package com.wakeUpTogetUp.togetUp.api.fcmNotification.entity;

import com.wakeUpTogetUp.togetUp.api.room.model.Room;
import com.wakeUpTogetUp.togetUp.api.users.fcmToken.FcmToken;
import com.wakeUpTogetUp.togetUp.utils.TimestampFormatter;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "notification")
@Getter
@Setter
@NoArgsConstructor
public class Notification {

//    @PrePersist
//    void prePersist() {
//        this.createdAt = TimestampFormatter.format(new Timestamp(System.currentTimeMillis()));
//    }

    @Builder
    public Notification(String title, String content, Room room, FcmToken fcmToken) {
        this.title = title;
        this.content = content;
        this.room = room;
        this.fcmToken = fcmToken;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( columnDefinition = "INT UNSIGNED")
    private Integer id;

    private String title;

    private String content;

    @Column(columnDefinition = "timestamp")
    private String createdAt = TimestampFormatter.format(new Timestamp(System.currentTimeMillis()));

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomId",columnDefinition = "INT UNSIGNED")
    private Room room;

    // TODO : 맞는지 확인!
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fcmTokenId",columnDefinition = "INT UNSIGNED")
    private FcmToken fcmToken;
}
