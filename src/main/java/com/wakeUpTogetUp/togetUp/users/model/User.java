package com.wakeUpTogetUp.togetUp.users.model;

import com.wakeUpTogetUp.togetUp.mappingGroupUser.model.MappingGroupUser;
import com.wakeUpTogetUp.togetUp.users.LoginType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


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

    @Column(name = "email",unique = true)
    private String email;
    @Column(name = "password")
    private String password;
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
    @Column(name = "loginType", length = 20)
    @Enumerated(EnumType.STRING)
    @NotNull
    private LoginType loginType ;


    /**
     * fcm 추가된 부분
     */
    private String fcmToken;
    private Timestamp tokenUpdated;


    @OneToMany(mappedBy = "user")
    private List<MappingGroupUser> mappingGroupUsers = new ArrayList<>();

    @PrePersist
    void registeredAt() {
        this.createdAt = Timestamp.from(Instant.now());
        this.tokenUpdated = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }


    @Builder
    public User(Integer id, String password, String username, String email, LoginType loginType) {
        this.id = id;
        this.password=password;
        this.userName = username;
        this.email = email;
        this.loginType=loginType;
    }

    // TODO : 추가한 builder 이후 하나로 통일 부탁해!
    @Builder
    public User(Integer id, String password, String username, String email, LoginType loginType, String fcmToken) {
        this.id = id;
        this.password=password;
        this.userName = username;
        this.email = email;
        this.loginType=loginType;
        this.fcmToken=fcmToken;
    }
}
