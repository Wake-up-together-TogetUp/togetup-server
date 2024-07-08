package com.wakeUpTogetUp.togetUp.api.room.model;


import com.wakeUpTogetUp.togetUp.api.alarm.model.Alarm;
import com.wakeUpTogetUp.togetUp.common.Constant;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
@SQLDelete(sql = "UPDATE room SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
@Table(name = "room")
@Getter
@Setter
@NoArgsConstructor()
@DynamicInsert
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id = null;


    @Column(name = "name", length = 10)
    private String name;

    @Column(name = "intro", length = 30)
    private String intro;


    @Column(name = "topic")
    private String topic;

    @Column(name = "invitation_code", length = 10)
    private String invitationCode;


    @OneToMany(mappedBy = "room",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomUser> roomUsers = new ArrayList<>();


    @OneToMany(mappedBy = "room",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Alarm> alarms = new ArrayList<>();

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @PrePersist
    void createdAt() {
        this.createdAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }

    @Builder
    public Room(Integer id, String name, String intro) {
        this.id = id;
        this.name = name;
        this.intro = intro;
        this.topic = UUID.randomUUID().toString();
        this.invitationCode = UUID.randomUUID().toString().substring(0, Constant.INVITATION_CODE_LENGTH);
    }

    public void join(RoomUser newRoomUser){
        checkAleadyJoin(newRoomUser);
        checkRoomCapacity();
        roomUsers.add(newRoomUser);
    }
    public boolean isEmptyRoom() {
        return this.roomUsers.size() == 0;
    }

    public boolean isUserInRoom(Integer userId) {
        return roomUsers.stream()
                .anyMatch(roomUser -> userId == roomUser.getUser().getId());
    }

    public void checkRoomCapacity() {
        if (roomUsers.size() >= 10) {
            throw new BaseException(Status.ROOM_FULL);
        }
    }

    public void checkAleadyJoin(RoomUser newRoomUser){

        if (isUserInRoom(newRoomUser.getUser().getId())) {
            throw new BaseException(Status.ROOM_USER_ALREADY_EXIST);
        }
    }
    public void leaveRoom(Integer userId){
        if(!isUserInRoom(userId)) {
            throw new BaseException(Status.ROOM_USER_NOT_FOUND);
        }
        RoomUser userToRemove = getUserInRoom(userId);
        roomUsers.remove(userToRemove);
    }

    private RoomUser getUserInRoom(Integer userId){
        return roomUsers.stream()
                .filter(roomUser -> userId.equals(roomUser.getUser().getId()))
                .findFirst()
                .orElseThrow(() -> new BaseException(Status.ROOM_USER_NOT_FOUND));
    }

}


