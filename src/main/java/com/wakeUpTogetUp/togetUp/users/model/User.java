package com.wakeUpTogetUp.togetUp.users.model;

import com.wakeUpTogetUp.togetUp.users.model.UserRole;
import com.wakeUpTogetUp.togetUp.login.oauth.entity.ProviderType;
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



@Setter
@Getter
@Entity
@Table(name="user")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id = null;

    @Column(name = "name")
    private String userName;

//    @Column(name = "nickName")
//    private String nickName;

    @Column(name = "email")
    private String email;
//    @Column(name = "password")
//    private String password;
//
//    @Column(name = "phoneNumber")
//    private String phoneNumber;

    @Column(name = "statusMessage")
    private String statusMessage;

//    @Column(name = "avatarImgLink")
//    private String avatarImgLink;

    @Column(name = "createdAt")
    private Timestamp createdAt;

    @Column(name = "updatedAt")
    private Timestamp updatedAt;

//    @Column(name = "removedAt")
//    private Timestamp removedAt;

    //추가
//    @Column(name = "PROVIDER_TYPE", length = 20)
//    @Enumerated(EnumType.STRING)
//    @NotNull
//    private ProviderType providerType;

    @PrePersist
    void registeredAt() {
        this.createdAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }


    @Builder
    public User(Integer id, String username,  String email) {
        this.id = id;
        this.userName = username;
        this.email = email;
    }

}
