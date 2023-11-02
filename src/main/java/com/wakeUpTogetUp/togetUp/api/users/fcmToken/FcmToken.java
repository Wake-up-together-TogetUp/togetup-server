package com.wakeUpTogetUp.togetUp.api.users.fcmToken;

import com.wakeUpTogetUp.togetUp.api.users.model.User;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "fcm_token")
@Getter
@Setter
@NoArgsConstructor(force = true)
@Builder
@AllArgsConstructor
public class FcmToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }

    public void updateFcmToken(String fcmToken) {
        setValue(fcmToken);
    }


}
