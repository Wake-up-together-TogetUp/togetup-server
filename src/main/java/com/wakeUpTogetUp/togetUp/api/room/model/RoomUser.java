package com.wakeUpTogetUp.togetUp.api.room.model;

import com.wakeUpTogetUp.togetUp.api.users.model.User;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
@Entity
@Table(name = "room_user")
@Getter
@Setter
@Builder
@AllArgsConstructor
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

    @Column(name = "is_host")
    private Boolean isHost;

    @Column(name = "agree_push")
    private Boolean agreePush;

    @PrePersist
    void createdAt() {
        this.createdAt = Timestamp.from(Instant.now());
    }


    public void setUser(User user) {
        this.user = user;
        user.getRoomUsers().add(this);
    }



}
