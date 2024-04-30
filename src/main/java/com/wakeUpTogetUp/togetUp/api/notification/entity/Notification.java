package com.wakeUpTogetUp.togetUp.api.notification.entity;

import com.wakeUpTogetUp.togetUp.api.room.model.Room;
import com.wakeUpTogetUp.togetUp.api.users.fcmToken.FcmToken;
import com.wakeUpTogetUp.togetUp.utils.TimeFormatter;
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


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private Integer id;

    private String title;

    private String content;

    @Column(columnDefinition = "timestamp")
    private String createdAt = TimeFormatter.timestampFormat(new Timestamp(System.currentTimeMillis()));

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomId", columnDefinition = "INT UNSIGNED")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fcmTokenId", columnDefinition = "INT UNSIGNED")
    private FcmToken fcmToken;
}
