package com.wakeUpTogetUp.togetUp.api.notification.entity;

import com.wakeUpTogetUp.togetUp.api.notification.DataMapToStringConverter;
import com.wakeUpTogetUp.togetUp.api.users.domain.User;
import com.wakeUpTogetUp.togetUp.api.users.fcmToken.FcmToken;
import com.wakeUpTogetUp.togetUp.utils.TimeFormatter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Map;

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

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Convert(converter = DataMapToStringConverter.class)
    @Column(columnDefinition = "TEXT")
    private Map<String, String> dataMap;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "is_read")
    private boolean isRead;

}
