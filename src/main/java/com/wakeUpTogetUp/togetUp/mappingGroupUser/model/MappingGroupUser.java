package com.wakeUpTogetUp.togetUp.mappingGroupUser.model;

import com.wakeUpTogetUp.togetUp.group.model.Group;
import com.wakeUpTogetUp.togetUp.users.model.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "mappingGroupUser")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)//모든 정보를 다 가지고 있어야함. 무분별한 객체생성을 막음.
public class MappingGroupUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id = null;


    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "groupId")
    private Group group;

    //개인미션 성공 알림여부

    @Column(name="isPersonalNotice",columnDefinition = "TINYINT", length=1)
    @ColumnDefault("1")
    private int isPersonalNotice;

    //채팅방 알림여부
    @Column(name="isNotice", columnDefinition = "TINYINT", length=1)
    @ColumnDefault("1")
    private int isNotice;

    @Column(name="isHostUser", columnDefinition = "TINYINT", length=1)
    @ColumnDefault("0")
    private int isHostUser;

    @Column(name="isActivated",columnDefinition = "TINYINT", length=1)
    @ColumnDefault("1")
    private int isActivated;

    @Column(name = "createdAt")
    private Timestamp createdAt;

    @Column(name = "updatedAt")
    private Timestamp updatedAt;


    @PrePersist
    void createdAt() {
        this.createdAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }
}
