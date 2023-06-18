package com.wakeUpTogetUp.togetUp.fcmNotification.entity;

import com.wakeUpTogetUp.togetUp.users.model.User;
import com.wakeUpTogetUp.togetUp.utils.TimestampFormatter;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "pushLog")
@Getter
@Setter
@NoArgsConstructor
public class PushLog {
    @Builder
    public PushLog(String title, String message, User receiver) {
        this.title = title;
        this.message = message;
        this.receiver = receiver;
    }

    @PrePersist
    void prePersist() {
        this.sendAt = TimestampFormatter.format(new Timestamp(System.currentTimeMillis()));
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String message;
    @Column(columnDefinition = "timestamp")
    private String sendAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiverId", columnDefinition = "int unsigned")
    private User receiver;
}
