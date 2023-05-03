package com.wakeUpTogetUp.togetUp.mappingGroupUser.model;

import com.wakeUpTogetUp.togetUp.group.model.Group;
import com.wakeUpTogetUp.togetUp.users.model.User;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Entity
@Table(name = "mappingGroupUser")
@Getter
@Setter
@NoArgsConstructor(force = true)
public class MappingGroupUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id = null;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @NonNull
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupId")
    @NonNull
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



//    @Builder
//    public MappingGroupUser(Integer id,Integer isHostUser) {
//
//        this.id = id;
////        this.user=user;
////        this.group=group;
//        this.isHostUser=isHostUser;
//
//
//    }

    @Builder
    public static MappingGroupUser of( Integer id ,User user,Group group,Integer isHostUser) {
        MappingGroupUser groupUser = new MappingGroupUser();
        groupUser.setId(id);
        groupUser.setUser(user);
        groupUser.setGroup(group);
        groupUser.setIsHostUser(isHostUser);
        return groupUser;
    }
    public void setUser(User user) {
        this.user = user;
        user.getMappingGroupUsers().add(this);
    }

    public void setGroup(Group group) {
        this.group = group;
        group.getMappingGroupUsers().add(this);
    }
}
