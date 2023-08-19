package com.wakeUpTogetUp.togetUp.api.users.fcmToken;

import com.wakeUpTogetUp.togetUp.api.users.model.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "fcm_token")
@Getter
@Setter
@NoArgsConstructor(force = true)
@Data
public class FcmToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
