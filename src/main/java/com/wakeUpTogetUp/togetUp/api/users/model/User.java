package com.wakeUpTogetUp.togetUp.api.users.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wakeUpTogetUp.togetUp.api.room.model.RoomUser;
import com.wakeUpTogetUp.togetUp.api.auth.LoginType;
import com.wakeUpTogetUp.togetUp.api.users.fcmToken.FcmToken;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@Entity
@SQLDelete(sql = "UPDATE user SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
@Table(name="user")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private Integer id = null;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "social_id")
    private String socialId;


    @Column(name = "login_type", length = 20)
    @Enumerated(EnumType.STRING)
    @NotNull
    private LoginType loginType ;

    @Column(name = "agree_push")
    private boolean agreePush;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "is_deleted")
    private boolean isDeleted ;


    @OneToMany(mappedBy = "user")
    private List<FcmToken> fcmToken = new ArrayList<>();


    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<RoomUser> roomUsers = new ArrayList<>();

    @PrePersist
    void registeredAt() {
        this.createdAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }


    @Builder
    public User(Integer id, String socialId, String name,String email, LoginType loginType) {
        this.id = id;
        this.socialId=socialId;
        this.name = name;
        this.email=email;
        this.loginType=loginType;
    }
}
