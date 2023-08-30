package com.wakeUpTogetUp.togetUp.api.room.model;

import com.wakeUpTogetUp.togetUp.api.users.model.User;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.stream.Collectors;
@ToString
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
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;



    @Column(name = "created_at")
    private Timestamp createdAt;

    @PrePersist
    void createdAt() {
        this.createdAt = Timestamp.from(Instant.now());
    }




    @Builder
    private RoomUser(Integer id , User user, Room room) {
        this.id=id;
        this.user=user;
        this.room = room;
    }

    public void setUser(User user) {
        this.user = user;
        user.getRoomUsers().add(this);
    }



}
