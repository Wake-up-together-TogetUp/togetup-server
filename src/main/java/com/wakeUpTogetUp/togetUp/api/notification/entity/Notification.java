package com.wakeUpTogetUp.togetUp.api.notification.entity;

import com.wakeUpTogetUp.togetUp.api.notification.DataMapToStringConverter;
import com.wakeUpTogetUp.togetUp.api.notification.NotificationDataKeyType;
import com.wakeUpTogetUp.togetUp.api.notification.NotificationDataValueType;
import com.wakeUpTogetUp.togetUp.api.notification.vo.NotificationSendEvent;
import com.wakeUpTogetUp.togetUp.api.users.domain.User;
import com.wakeUpTogetUp.togetUp.api.users.fcmToken.FcmToken;
import com.wakeUpTogetUp.togetUp.utils.TimeFormatter;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
@Table(name = "notification")
@Getter
@Builder
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private Integer id;

    private String title;

    private String body;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Convert(converter = DataMapToStringConverter.class)
    @Column(columnDefinition = "TEXT")
    private Map<String, String> dataMap;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "is_read" )
    private Boolean isRead ;

    @PrePersist
    void createdAt() {
        this.createdAt = Timestamp.from(Instant.now());
    }


    public static List<Notification> from(NotificationSendEvent noti) {
        return noti.getFcmTokens().stream()
                .map(FcmToken::getUser)
                .distinct()
                .map(user -> Notification.builder()
                        .title(noti.getTitle())
                        .body(noti.getBody())
                        .dataMap(noti.getDataMap())
                        .userId(user.getId())
                        .build())
                .collect(Collectors.toList());
    }

}
