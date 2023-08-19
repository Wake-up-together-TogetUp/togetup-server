package com.wakeUpTogetUp.togetUp.api.group.model;

import com.wakeUpTogetUp.togetUp.api.users.model.User;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "room_user")
@Getter
@Setter
@NoArgsConstructor(force = true)
public class RoomUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( columnDefinition = "INT UNSIGNED")
    private Integer id = null;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @NonNull
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupId")
    @NonNull
    private Room room;

    //개인미션 성공 알림여부

    @Column(name="isPersonalNotice",columnDefinition = "TINYINT", length=1)
    @ColumnDefault("1")
    private Integer isPersonalNotice;

    //채팅방 알림여부
    @Column(name="isNotice", columnDefinition = "TINYINT", length=1)
    @ColumnDefault("1")
    private Integer isNotice;

    @Column(name="isHostUser", columnDefinition = "TINYINT", length=1)
    @ColumnDefault("0")
    private Integer isHostUser;

    @Column(name="isActivated",columnDefinition = "TINYINT", length=1)
    @ColumnDefault("1")
    private Integer isActivated;

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
    private RoomUser(Integer id , User user, Room room, Integer isPersonalNotice, Integer isNotice, Integer isHostUser) {
        this.id=id;
        this.user=user;
        this.room = room;
        this.isPersonalNotice=isPersonalNotice;
        this.isNotice=isNotice;
        this.isHostUser=isHostUser;

    }
    public void setUser(User user) {
        this.user = user;
        user.getRoomUsers().add(this);
    }

//    public void setRoom(Room room) {
//        this.room = room;
//        room.getMappingRoomUsers().add(this);
//    }
}
